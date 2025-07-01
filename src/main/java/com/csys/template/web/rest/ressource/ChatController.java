package com.csys.template.web.rest.ressource;

import com.csys.template.domain.ChatMessage;
import com.csys.template.service.ChatMessageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.csys.template.dtoRequest.ChatMessageRequest;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;

    /**
     * Constructeur pour l'injection de dépendances (pratique recommandée).
     * @param messagingTemplate Opérations pour envoyer des messages WebSocket.
     * @param chatMessageService Service pour la logique métier du chat.
     */
    public ChatController(SimpMessageSendingOperations messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    /**
     * Gère les messages entrants envoyés à la destination "/app/chat.sendMessage".
     * Il sauvegarde le message, le diffuse à la salle de discussion spécifique
     * et le diffuse également à un topic public pour l'administrateur.
     *
     * @param messageRequest Le DTO contenant les informations du message envoyé par le client.
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageRequest messageRequest) {
        log.debug("Nouveau message de chat reçu pour traitement : {}", messageRequest);
        try {
            // 1. Sauvegarder le message en utilisant le service.
            // Cette méthode devrait retourner l'entité sauvegardée avec toutes les informations nécessaires (expéditeur, salle, etc.).
            ChatMessage savedMessage = chatMessageService.saveMessage(
                messageRequest.getChatRoomId(),
                messageRequest.getSenderId(),
                messageRequest.getContent()
            );
            log.info("Message sauvegardé en base de données : {}", savedMessage.getId());

            // 2. Envoyer le message aux participants de la discussion via le topic de la salle.
            String roomDestination = "/topic/chat/" + savedMessage.getChatRoom().getId();
            log.debug("Diffusion du message vers la salle de discussion : {}", roomDestination);
            messagingTemplate.convertAndSend(roomDestination, savedMessage);

            // 3. **ACTION ESSENTIELLE** : Envoyer le message sur le topic public.
            // L'administrateur est abonné à ce topic pour superviser toutes les conversations.
            log.debug("Diffusion du message vers le topic public pour l'administrateur.");
            messagingTemplate.convertAndSend("/topic/public", savedMessage);

            // 4. (Fonctionnalité conservée) Envoyer une confirmation privée à l'expéditeur.
            // Utile pour les accusés de réception ou les notifications personnelles.
            // Nécessite que le Principal de la session WebSocket soit correctement configuré avec le login de l'utilisateur.
            if (savedMessage.getSender() != null && savedMessage.getSender().getLogin() != null) {
                messagingTemplate.convertAndSendToUser(
                    savedMessage.getSender().getLogin(),
                    "/queue/messages", // La destination privée de l'utilisateur
                    savedMessage
                );
                log.debug("Confirmation privée envoyée à l'utilisateur : {}", savedMessage.getSender().getLogin());
            }

        } catch (Exception e) {
            log.error("Une erreur est survenue lors du traitement du message de chat : " + e.getMessage(), e);
            // Envisager d'envoyer un message d'erreur à l'expéditeur pour l'informer de l'échec.
        }
    }
}
