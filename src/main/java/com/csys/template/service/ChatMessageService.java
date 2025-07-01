package com.csys.template.service;

import com.csys.template.domain.ChatMessage;
import com.csys.template.domain.ChatRoom;
import com.csys.template.domain.Utilisateur;
import com.csys.template.repository.ChatMessageRepository;
import com.csys.template.repository.ChatRoomRepository;
import com.csys.template.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository, UtilisateurRepository utilisateurRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public ChatMessage saveMessage(Integer chatRoomId, Integer senderId, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("ChatRoom non trouvée avec l'ID: " + chatRoomId));
        Utilisateur sender = utilisateurRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID: " + senderId));

        ChatMessage newMessage = new ChatMessage();
        newMessage.setChatRoom(chatRoom);
        newMessage.setSender(sender);
        newMessage.setContent(content);
        newMessage.setTimestamp(LocalDateTime.now());
        
        // Par défaut, seul l'expéditeur a "lu" le message qu'il vient d'envoyer.
        newMessage.getReadByUsers().add(sender);

        return chatMessageRepository.save(newMessage);
    }

    @Transactional(readOnly = true)
    public List<ChatMessage> getMessagesByChatRoomId(Integer chatRoomId) {
        return chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }

    /**
     * --- IMPLÉMENTATION DE LA MÉTHODE MANQUANTE ---
     * Appelle la méthode du repository pour obtenir la liste des messages non lus.
     */
    @Transactional(readOnly = true)
    public List<ChatMessage> getUnreadMessagesForUser(Integer userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return chatMessageRepository.findUnreadMessagesForUser(userId);
    }
    
    /**
     * Méthode utilitaire pour marquer un message comme lu par un utilisateur.
     * Vous en aurez besoin lorsque l'utilisateur ouvrira une discussion.
     */
    public void markMessageAsRead(Integer messageId, Integer userId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message non trouvé avec l'ID: " + messageId));
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));
        
        message.getReadByUsers().add(user);
        chatMessageRepository.save(message);
    }
}
