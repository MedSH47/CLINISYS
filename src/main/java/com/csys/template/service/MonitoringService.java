// src/main/java/com/csys/template/service/MonitoringService.java
package com.csys.template.service;

import java.time.LocalDateTime;
import java.util.ArrayList; // Importez Commentaire si vous l'utilisez
import java.util.HashMap; // Importez DocumentJointes si vous l'utilisez
import java.util.List;
import java.util.Map; // Importez CommentaireRepository

import org.springframework.stereotype.Service; // Importez DocumentJointesRepository
import org.springframework.transaction.annotation.Transactional;

import com.csys.template.domain.Commentaire;
import com.csys.template.domain.DocumentJointes;
import com.csys.template.domain.Ticket;
import com.csys.template.repository.CommentaireRepository;
import com.csys.template.repository.DocumentJointesRepository;
import com.csys.template.repository.TicketRepository;

@Service
@Transactional
public class MonitoringService {
    private final TicketRepository ticketRepository;
    private final CommentaireRepository commentaireRepository; // Injectez si vous voulez compter les commentaires
    private final DocumentJointesRepository documentJointesRepository; // Injectez si vous voulez compter les documents joints

    public MonitoringService(TicketRepository ticketRepository,
                             CommentaireRepository commentaireRepository, // Ajoutez au constructeur
                             DocumentJointesRepository documentJointesRepository) { // Ajoutez au constructeur
        this.ticketRepository = ticketRepository;
        this.commentaireRepository = commentaireRepository;
        this.documentJointesRepository = documentJointesRepository;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getHourlyActivityCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursAgo = now.minusHours(24);

        // --- Récupérer les données brutes des activités pour les dernières 24 heures ---
        // Option 1: Récupérer tous les tickets et filtrer en mémoire (simple mais moins performant sur de grandes bases)
        // List<Ticket> recentTickets = ticketRepository.findAll().stream()
        //     .filter(t -> t.getDateCreation() != null && t.getDateCreation().isAfter(twentyFourHoursAgo))
        //     .collect(Collectors.toList());

        // Option 2: Si votre repository peut faire un findByDateCreationAfter (recommandé pour la performance)
        List<Ticket> recentTickets = ticketRepository.findByDateCreationAfter(twentyFourHoursAgo);
        List<Commentaire> recentComments = commentaireRepository.findByDateCommentaireAfter(twentyFourHoursAgo);
        List<DocumentJointes> recentDocuments = documentJointesRepository.findByDateDocumentAfter(twentyFourHoursAgo);


        // --- Agréger l'activité par heure ---
        Map<Integer, Long> hourlyCounts = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            hourlyCounts.put(twentyFourHoursAgo.plusHours(i).getHour(), 0L); // Initialise toutes les heures à 0
        }

        recentTickets.stream()
            .filter(t -> t.getDateCreation() != null)
            .forEach(t -> hourlyCounts.merge(t.getDateCreation().getHour(), 1L, Long::sum));

        recentComments.stream()
            .filter(c -> c.getDateCommentaire() != null)
            .forEach(c -> hourlyCounts.merge(c.getDateCommentaire().getHour(), 1L, Long::sum));

        recentDocuments.stream()
            .filter(d -> d.getDateDocument() != null)
            .forEach(d -> hourlyCounts.merge(d.getDateDocument().getHour(), 1L, Long::sum));


        // --- Formater la réponse pour le frontend (ordonnée par heure) ---
        List<Map<String, Object>> hourlyData = new ArrayList<>();
        // Déterminer l'heure de début pour l'affichage (celle d'il y a 24h)
        int startHour = twentyFourHoursAgo.getHour();

        for (int i = 0; i < 24; i++) {
            int currentHour = (startHour + i) % 24; // Calcul de l'heure actuelle en 24h
            hourlyData.add(new HashMap<String, Object>() {{
                put("hour", String.format("%02d:00", currentHour)); // Format "HH:00"
                put("count", hourlyCounts.getOrDefault(currentHour, 0L));
            }});
        }
        return hourlyData;
    }
}