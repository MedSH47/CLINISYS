package com.csys.template.web.rest.ressource;

import com.csys.template.domain.Notification;
import com.csys.template.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import com.csys.template.repository.UtilisateurRepository; // Assurez-vous d'importer ce repository
import com.csys.template.domain.Utilisateur;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final NotificationService notificationService;
    private final UtilisateurRepository utilisateurRepository; // Pour trouver l'utilisateur actuel

    @Autowired
    public NotificationResource(NotificationService notificationService, UtilisateurRepository utilisateurRepository) {
        this.notificationService = notificationService;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * GET /notifications : Récupère toutes les notifications pour l'utilisateur authentifié.
     */
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getUserNotifications(Principal principal) {
        Utilisateur currentUser = utilisateurRepository.findByLogin(principal.getName());
        if (currentUser == null) {
            return ResponseEntity.status(401).build(); // Non autorisé
        }
        List<Notification> notifications = notificationService.findByUser(currentUser.getId());
        return ResponseEntity.ok(notifications);
    }

    /**
     * POST /notifications/{id}/read : Marque une notification spécifique comme lue.
     */
    @PostMapping("/notifications/{id}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Integer id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}