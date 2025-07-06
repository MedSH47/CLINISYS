package com.csys.template.web.rest.ressource;

import com.csys.template.domain.ChatMessage;
import com.csys.template.service.ChatMessageService;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatResource {

    private static final Logger log = LoggerFactory.getLogger(ChatResource.class);

    @Autowired
    private ChatMessageService chatMessageService;
    
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat.sendPrivate")
    public void sendPrivate(@Payload ChatMessage messagePayload, Principal principal) {
        if (principal == null) {
            log.error("[DEBUG] Principal (authenticated user) is NULL. WebSocket security may not be configured correctly.");
            return;
        }
        log.info("[DEBUG] ===== NEW PRIVATE MESSAGE RECEIVED =====");
        log.info("[DEBUG] Authenticated Sender (Principal): {}", principal.getName());
        log.info("[DEBUG] Payload Received: Sender ID = {}, Receiver ID = {}, Content = '{}'", 
                 messagePayload.getSender(), messagePayload.getReceiver(), messagePayload.getContent());

        if (messagePayload.getReceiver() == null) {
            log.error("[DEBUG] CRITICAL: Receiver ID in payload is NULL. Cannot process message.");
            return;
        }
        
        try {
            // 1. Déléguer au service transactionnel
            log.info("[DEBUG] Calling ChatMessageService to process and save...");
            ChatMessage savedMessage = chatMessageService.processAndSavePrivateMessage(messagePayload, principal);
            log.info("[DEBUG] Service returned a saved message with ID: {}", savedMessage.getId());
            
            // 2. Récupérer les informations pour la diffusion
            String senderLogin = principal.getName();
            String receiverLogin = savedMessage.getReceiverDetails().getLogin();
            
            if (receiverLogin == null) {
                log.error("[DEBUG] CRITICAL: Receiver login is NULL after fetching from DB. Cannot send message.");
                return;
            }

            // 3. Diffuser le message aux deux utilisateurs
            log.info("[DEBUG] Sending message ID {} to sender's queue: /user/{}/queue/private", savedMessage.getId(), senderLogin);
            messagingTemplate.convertAndSendToUser(senderLogin, "/queue/private", savedMessage);
            
            log.info("[DEBUG] Sending message ID {} to receiver's queue: /user/{}/queue/private", savedMessage.getId(), receiverLogin);
            messagingTemplate.convertAndSendToUser(receiverLogin, "/queue/private", savedMessage);
            
            log.info("[DEBUG] ===== MESSAGE PROCESSED SUCCESSFULLY =====");

        } catch (Exception e) {
            log.error("[DEBUG] !!!!! EXCEPTION CAUGHT WHILE PROCESSING MESSAGE !!!!!", e);
            // Notifier l'expéditeur de l'erreur
            messagingTemplate.convertAndSendToUser(
                principal.getName(), 
                "/queue/errors", 
                "Erreur serveur: " + e.getMessage()
            );
        }
    }
}