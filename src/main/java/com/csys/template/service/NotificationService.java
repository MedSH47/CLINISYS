package com.csys.template.service;

import com.csys.template.dtoResponse.NotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Sends a notification to a specific user.
     * The client should subscribe to a user-specific topic, e.g., /topic/private/{userId}
     *
     * @param userId  The ID of the user to notify.
     * @param message The notification message content.
     */
    public void notifyUser(Integer userId, String message) {
        String destination = "/topic/private/" + userId;
        NotificationDTO notification = new NotificationDTO(message);
        log.debug("Sending notification to user {}: {}", userId, message);
        messagingTemplate.convertAndSend(destination, notification);
    }
}
