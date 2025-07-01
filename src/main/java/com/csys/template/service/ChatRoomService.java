package com.csys.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;



import com.csys.template.domain.ChatRoom;
import com.csys.template.domain.ChatMessage;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoResponse.ChatRoomResponse;
import com.csys.template.domain.enum_identifier.ChatType;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.repository.ChatRoomRepository;
import com.csys.template.repository.ChatMessageRepository;



@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UtilisateurService userService; // Assurez-vous d'avoir ce repository

    @Transactional
    public ChatRoomResponse createPrivateChatRoom(List<Integer> participantIds) {
        if (participantIds == null || participantIds.size() != 2) {
            throw new IllegalArgumentException("A private chat must have exactly two participants.");
        }

        // Check if a private chat already exists between these two users
        Optional<ChatRoom> existingChat = chatRoomRepository.findPrivateChatRoomByParticipantIds(participantIds, 2);
        if (existingChat.isPresent()) {
            return mapToChatRoomResponse(existingChat.get());
        }

        Set<Utilisateur> participants = new HashSet<>(userService.findAllById(participantIds));
        if (participants.size() != 2) {
            throw new IllegalArgumentException("Invalid participant IDs provided.");
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setType(ChatType.PRIVATE);
        chatRoom.setCreatedAt(LocalDateTime.now());
        chatRoom.setParticipants(participants);
        chatRoom = chatRoomRepository.save(chatRoom);
        return mapToChatRoomResponse(chatRoom);
    }

    @Transactional
    public ChatRoomResponse createGroupChatRoom(String name, List<Integer> participantIds) {
        if (participantIds == null || participantIds.isEmpty()) {
            throw new IllegalArgumentException("A group chat must have at least one participant.");
        }

        Set<Utilisateur> participants = new HashSet<>(userService.findAllById(participantIds));
        if (participants.isEmpty()) {
            throw new IllegalArgumentException("Invalid participant IDs provided.");
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setType(ChatType.GROUP);
        chatRoom.setName(name);
        chatRoom.setCreatedAt(LocalDateTime.now());
        chatRoom.setParticipants(participants);
        chatRoom = chatRoomRepository.save(chatRoom);
        return mapToChatRoomResponse(chatRoom);
    }

    public List<ChatRoomResponse> getUserChatRooms(Integer userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByParticipants_Id(userId);
        return chatRooms.stream()
                .map(this::mapToChatRoomResponse)
                .collect(Collectors.toList());
    }

    public Optional<ChatRoom> getChatRoomById(Integer chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    @Transactional
    public void updateLastMessage(Integer chatRoomId, String content, LocalDateTime timestamp) {
        chatRoomRepository.findById(chatRoomId).ifPresent(chatRoom -> {
            chatRoom.setLastMessageContent(content);
            chatRoom.setLastMessageTimestamp(timestamp);
            chatRoomRepository.save(chatRoom);
        });
    }

    // Helper to map ChatRoom entity to ChatRoomResponse DTO
    private ChatRoomResponse mapToChatRoomResponse(ChatRoom chatRoom) {
        ChatRoomResponse response = new ChatRoomResponse();
        response.setId(chatRoom.getId());
        response.setType(chatRoom.getType());
        response.setName(chatRoom.getName());
        response.setCreatedAt(chatRoom.getCreatedAt());
        response.setLastMessageContent(chatRoom.getLastMessageContent());
        response.setLastMessageTimestamp(chatRoom.getLastMessageTimestamp());
        response.setParticipants(chatRoom.getParticipants());
        return response;
    }
}