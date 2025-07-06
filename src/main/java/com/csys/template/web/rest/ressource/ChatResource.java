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

/**
 * Contrôleur pour gérer les messages WebSocket en temps réel.
 * Cette version inclut un code de test pour la diffusion publique.
 */
@Controller
public class ChatResource {

    private static final Logger log = LoggerFactory.getLogger(ChatResource.class);

    private final ChatMessageService chatMessageService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public ChatResource(ChatMessageService chatMessageService, SimpMessageSendingOperations messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendPrivate")
    public void sendPrivate(@Payload ChatMessage messagePayload, Principal principal) {
        log.info("CONTROLLER: Message privé reçu de l'utilisateur authentifié '{}'", principal.getName());
        
        try {
            // 1. Déléguer la logique de sauvegarde au service transactionnel.
            ChatMessage savedMessage = chatMessageService.processAndSavePrivateMessage(messagePayload, principal);
            
            // 2. Récupérer les informations nécessaires pour la diffusion.
            String senderLogin = principal.getName();
            String receiverLogin = savedMessage.getReceiverDetails().getLogin();
            
            // --- DÉBUT DU BLOC DE TEST ---

            // A. On crée un ID de conversation unique et prévisible.
            // Le tri des ID garantit que la conversation entre 1 et 2 a le même topic que celle entre 2 et 1.
            Integer user1 = savedMessage.getSender();
            Integer user2 = savedMessage.getReceiver();
            String conversationId = user1 < user2 ? user1 + "-" + user2 : user2 + "-" + user1;
            String publicTopic = "/topic/chat/" + conversationId;

            log.info("✅ [TEST] Diffusion du message ID {} sur le TOPIC PUBLIC : {}", savedMessage.getId(), publicTopic);
            // B. On envoie le message sur ce topic public que les deux utilisateurs écouteront.
            messagingTemplate.convertAndSend(publicTopic, savedMessage);

            // --- FIN DU BLOC DE TEST ---


            // On conserve la logique originale pour voir si elle fonctionne en parallèle.
            log.info("[DEBUG] Envoi du message ID {} à la file d'attente PRIVÉE de l'expéditeur : /user/{}/queue/private", savedMessage.getId(), senderLogin);
            messagingTemplate.convertAndSendToUser(senderLogin, "/queue/private", savedMessage);
            
            log.info("[DEBUG] Envoi du message ID {} à la file d'attente PRIVÉE du destinataire : /user/{}/queue/private", savedMessage.getId(), receiverLogin);
            messagingTemplate.convertAndSendToUser(receiverLogin, "/queue/private", savedMessage);
            
            log.info("CONTROLLER: Message ID {} traité.", savedMessage.getId());

        } catch (Exception e) {
            log.error("CONTROLLER: Échec du traitement du message privé. Erreur: {}", e.getMessage(), e);
            messagingTemplate.convertAndSendToUser(
                principal.getName(), 
                "/queue/errors", 
                "Le message n'a pas pu être envoyé. Cause : " + e.getMessage()
            );
        }
    }
}