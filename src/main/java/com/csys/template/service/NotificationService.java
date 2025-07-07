package com.csys.template.service;

import com.csys.template.domain.Notification;
import com.csys.template.domain.Utilisateur;
import com.csys.template.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (user == null) {
            log.error("Tentative de création d'une notification pour un utilisateur null.");
            return;
        }

        // 1. Créer et sauvegarder l'entité notification
        Notification notification = new Notification();
        notification.setUtilisateur(user);
        notification.setMessage(message);
        notification.setLink(link);
        notification.setRead(false);
        Notification savedNotification = notificationRepository.save(notification);
        log.info("Notification sauvegardée avec l'ID {}", savedNotification.getId());

        // 2. Pousser la notification en temps réel via WebSocket
        // La destination est privée à l'utilisateur, basée sur son login.
        String destination = "/user/" + user.getLogin() + "/queue/notifications";
        
        log.info("Envoi de la notification en temps réel à la destination : {}", destination);
        messagingTemplate.convertAndSend(destination, savedNotification);
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