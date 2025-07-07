package com.csys.template.service;

import com.csys.template.domain.ChatMessage;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.MessageType;
import com.csys.template.dtoProjection.ChatContactDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.ChatMessageRepository;
import com.csys.template.repository.UtilisateurRepository;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service pour gérer la logique métier du chat.
 * L'annotation @Service en fait un bean Spring.
 * Les méthodes sont transactionnelles pour assurer la cohérence des données.
 */
@Service
@Transactional
public class ChatMessageService {

    private static final Logger log = LoggerFactory.getLogger(ChatMessageService.class);
    
    private final ChatMessageRepository chatMessageRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, 
                              UtilisateurRepository utilisateurRepository, 
                              SimpMessageSendingOperations messagingTemplate) {
        this.chatMessageRepository = chatMessageRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Traite et sauvegarde un message texte privé, puis le diffuse aux participants.
     * @param messagePayload Le message reçu du client WebSocket.
     * @param principal L'objet représentant l'utilisateur authentifié (l'expéditeur).
     * @return Le message sauvegardé et enrichi avec les détails des utilisateurs.
     */
    public ChatMessage processAndSavePrivateMessage(ChatMessage messagePayload, Principal principal) {
        log.info("SERVICE: Traitement du message texte de {} vers l'utilisateur ID {}", principal.getName(), messagePayload.getReceiver());

        Utilisateur senderUser = utilisateurRepository.findByLogin(principal.getName());
        if (senderUser == null) {
            throw new IllegalArgumentException("L'utilisateur expéditeur n'existe pas : " + principal.getName());
        }
        
        Utilisateur receiverUser = utilisateurRepository.findById(messagePayload.getReceiver())
                .orElseThrow(() -> new IllegalArgumentException("Le destinataire avec l'ID " + messagePayload.getReceiver() + " n'existe pas."));

        messagePayload.setSender(senderUser.getId());
        
        ChatMessage savedMessage = chatMessageRepository.save(messagePayload);
        
        // Enrichir pour la notification WebSocket
        savedMessage.setSenderDetails(UtilisateurFactory.toDTOLight(senderUser));
        savedMessage.setReceiverDetails(UtilisateurFactory.toDTOLight(receiverUser));

        // Notifier l'expéditeur et le destinataire
        messagingTemplate.convertAndSendToUser(senderUser.getLogin(), "/queue/private", savedMessage);
        messagingTemplate.convertAndSendToUser(receiverUser.getLogin(), "/queue/private", savedMessage);
        
        return savedMessage;
    }
    
    /**
     * Sauvegarde un message contenant un fichier et notifie les participants via WebSocket.
     * @param file Le fichier uploadé.
     * @param senderId L'ID de l'expéditeur.
     * @param receiverId L'ID du destinataire.
     */
    public void saveAndSendFileMessage(MultipartFile file, Integer senderId, Integer receiverId) throws IOException {
        Utilisateur senderUser = utilisateurRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Expéditeur non trouvé avec l'ID: " + senderId));
        Utilisateur receiverUser = utilisateurRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Destinataire non trouvé avec l'ID: " + receiverId));

        String messageTypeName = file.getContentType() != null && file.getContentType().startsWith("image/") ? "IMAGE" : "FILE";
        
        ChatMessage chatMessage = ChatMessage.builder()
                .sender(senderId)
                .receiver(receiverId)
                .content(String.format("[%s]", file.getOriginalFilename())) // Un placeholder pour le contenu texte
                .type(MessageType.valueOf(messageTypeName))
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileContent(file.getBytes()) // Stocke le contenu binaire du fichier
                .build();
        
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        
        // Enrichir pour la notification WebSocket (sans le contenu binaire lourd)
        savedMessage.setSenderDetails(UtilisateurFactory.toDTOLight(senderUser));
        savedMessage.setReceiverDetails(UtilisateurFactory.toDTOLight(receiverUser));
        
        // Notifier l'expéditeur et le destinataire
        messagingTemplate.convertAndSendToUser(senderUser.getLogin(), "/queue/private", savedMessage);
        messagingTemplate.convertAndSendToUser(receiverUser.getLogin(), "/queue/private", savedMessage);
        
        log.info("Message fichier #{} de {} à {} a été sauvegardé et notifié.", savedMessage.getId(), senderUser.getLogin(), receiverUser.getLogin());
    }

    /**
     * Récupère le contenu binaire d'un message spécifique.
     * @param messageId L'ID du message contenant le fichier.
     * @return L'entité ChatMessage complète, incluant le `fileContent`.
     */
    @Transactional(readOnly = true)
    public ChatMessage getFileMessage(Integer messageId) {
        return chatMessageRepository.findById(messageId)
                .filter(msg -> msg.getFileContent() != null)
                .orElseThrow(() -> new RuntimeException("Fichier non trouvé pour le message ID: " + messageId));
    }

    /**
     * Récupère l'historique de chat entre deux utilisateurs.
     * @param user1 L'ID du premier utilisateur.
     * @param user2 L'ID du second utilisateur.
     * @return Une liste de messages enrichis avec les détails de l'expéditeur/destinataire.
     */
    @Transactional(readOnly = true)
    public List<ChatMessage> findByParticipants(Integer user1, Integer user2) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(user1, user2, user1, user2);
        
        // Enrichissement des messages
        messages.forEach(msg -> {
             utilisateurRepository.findById(msg.getSender()).ifPresent(user -> msg.setSenderDetails(UtilisateurFactory.toDTOLight(user)));
             utilisateurRepository.findById(msg.getReceiver()).ifPresent(user -> msg.setReceiverDetails(UtilisateurFactory.toDTOLight(user)));
        });
        return messages;
    }

    /**
     * Récupère les dernières conversations pour un utilisateur donné.
     * @param userId L'ID de l'utilisateur.
     * @return Une liste de DTOs représentant chaque contact et le dernier message échangé.
     */
    @Transactional(readOnly = true)
    public List<ChatContactDTO> findMyChatMessages(Integer userId) {
        List<ChatMessage> latestMessages = chatMessageRepository.findLatestMessageFromEachConversation(userId);
        
        return latestMessages.stream()
            .map(msg -> {
                Integer partnerId = msg.getSender().equals(userId) ? msg.getReceiver() : msg.getSender();
                return utilisateurRepository.findById(partnerId)
                        .map(partnerUser -> new ChatContactDTO(UtilisateurFactory.toDTOLight(partnerUser), msg))
                        .orElse(null);
            })
            .filter(contact -> contact != null)
            .collect(Collectors.toList());
    }
}