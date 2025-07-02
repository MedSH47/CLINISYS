package com.csys.template.web.rest.ressource;

import com.csys.template.domain.ChatMessage;
import com.csys.template.domain.Utilisateur;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.ChatMessageRepository;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.service.ChatMessageService;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatResource {

    private static final Logger log = LoggerFactory.getLogger(ChatResource.class);

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat.sendPrivate")
    public void sendPrivate(@Payload ChatMessage messagePayload, Principal principal) {
        log.info("BACKEND: Received private message: {}", messagePayload.getContent());
        
        try {
            Utilisateur senderUser = utilisateurRepository.findByLogin(principal.getName());
            Utilisateur receiverUser = utilisateurRepository.findById(messagePayload.getReceiver())
                    .orElseThrow(() -> new RuntimeException("Receiver user not found"));
            
            messagePayload.setSender(senderUser.getId());
            ChatMessage savedMessage = chatMessageRepository.save(messagePayload);
            log.info("BACKEND: Message saved to DB with ID: {}", savedMessage.getId());

            savedMessage.setSenderDetails(UtilisateurFactory.toDTOLight(senderUser));
            savedMessage.setReceiverDetails(UtilisateurFactory.toDTOLight(receiverUser));

            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/private", savedMessage);
            messagingTemplate.convertAndSendToUser(receiverUser.getLogin(), "/queue/private", savedMessage);
            log.info("BACKEND: Message sent to sender '{}' and receiver '{}'", principal.getName(), receiverUser.getLogin());

        } catch (Exception e) {
            log.error("BACKEND: Failed to process private message.", e);
        }
    }
}

// No changes needed for the history resource
@RestController
@RequestMapping("/api")
class ChatHistoryResource {
    @Autowired private ChatMessageService service;
    @Autowired private UtilisateurRepository utilisateurRepository;

    @GetMapping("/chat/history")
    public List<ChatMessage> getMessages(Integer user1, Integer user2) {
        List<ChatMessage> messages = service.findByParticipants(user1, user2);
        messages.forEach(msg -> {
             utilisateurRepository.findById(msg.getSender()).ifPresent(user -> msg.setSenderDetails(UtilisateurFactory.toDTOLight(user)));
             utilisateurRepository.findById(msg.getReceiver()).ifPresent(user -> msg.setReceiverDetails(UtilisateurFactory.toDTOLight(user)));
        });
        return messages;
    }
    
    @GetMapping("/chat/my-messages/{userId}")
    public List<ChatMessage> getMyMessages(@PathVariable Integer userId) {
        List<ChatMessage> latestMessages = service.findMyChatMessages(userId);
        latestMessages.forEach(msg -> {
            utilisateurRepository.findById(msg.getSender())
                .ifPresent(user -> msg.setSenderDetails(UtilisateurFactory.toDTOLight(user)));
            utilisateurRepository.findById(msg.getReceiver())
                .ifPresent(user -> msg.setReceiverDetails(UtilisateurFactory.toDTOLight(user)));
        });
        return latestMessages;
    }
}