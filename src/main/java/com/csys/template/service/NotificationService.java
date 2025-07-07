package com.csys.template.service;

import com.csys.template.domain.Notification;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoProjection.NotificationDTO;
import com.csys.template.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Crée, sauvegarde et pousse une notification en temps réel à un utilisateur.
     *
     * @param user    L'utilisateur qui doit recevoir la notification.
     * @param message Le message de la notification.
     * @param link    Un lien optionnel (ex: /tickets/123).
     */
    public void createAndSendNotification(Utilisateur user, String message, String link) {
        if (user == null || user.getLogin() == null || user.getLogin().isBlank()) {
            log.error("Tentative de création d'une notification pour un utilisateur avec login null ou vide.");
            return;
        }

        // 1. Créer et sauvegarder l'entité Notification
        Notification notification = new Notification();
        notification.setUtilisateur(user);
        notification.setMessage(message);
        notification.setLink(link);
        notification.setCreatedAt(LocalDateTime.now()); // si tu as ce champ
        notification.setRead(false);

        Notification savedNotification = notificationRepository.save(notification);
        log.info("Notification sauvegardée avec l'ID {}", savedNotification.getId());

        // 2. Préparer la destination WebSocket sécurisée
        String destination = "/user/" + user.getLogin() + "/queue/notifications";
        log.info("Envoi de la notification en temps réel à la destination : {}", destination);

        // 3. Utiliser un DTO pour éviter les erreurs de sérialisation
        NotificationDTO dto = new NotificationDTO();
        dto.setMessage(notification.getMessage());
        dto.setLink(notification.getLink());
        dto.setTimestamp(notification.getCreatedAt());

        try {
            messagingTemplate.convertAndSendToUser(user.getLogin(), "/queue/notifications", dto);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de la notification WebSocket : ", e);
        }
    }

    /**
     * NOUVEAU : Crée et envoie une notification à une liste d'utilisateurs.
     *
     * @param users   La liste des utilisateurs à notifier.
     * @param message Le message de la notification.
     * @param link    Un lien optionnel.
     */
    public void createAndSendNotificationToUsers(List<Utilisateur> users, String message, String link) {
        if (users == null || users.isEmpty()) {
            log.warn("Tentative d'envoyer une notification à une liste d'utilisateurs vide.");
            return;
        }
        for (Utilisateur user : users) {
            createAndSendNotification(user, message, link);
        }
    }


    /**
     * Récupère toutes les notifications d'un utilisateur.
     */
    @Transactional(readOnly = true)
    public List<Notification> findByUser(Integer userId) {
        return notificationRepository.findByUtilisateurIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Marque une notification comme lue.
     */
    public void markAsRead(Integer notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}