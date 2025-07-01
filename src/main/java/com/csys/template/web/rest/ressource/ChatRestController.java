package com.csys.template.web.rest.ressource;

import com.csys.template.domain.ChatMessage;
import com.csys.template.dtoRequest.CreateGroupChatRequest;
import com.csys.template.dtoRequest.CreatePrivateChatRequest;
import com.csys.template.dtoResponse.ChatRoomResponse;
import com.csys.template.service.ChatMessageService;
import com.csys.template.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Ce contrôleur gère toutes les opérations REST pour les discussions (Chat).
 * Il est aligné avec les appels effectués par chatService.js dans le frontend.
 */
@RestController
// CORRECTION 1: L'URL de base est maintenant "/api/chat" pour correspondre au frontend.
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatRestController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    // Utilisation de l'injection par constructeur (meilleure pratique)
    public ChatRestController(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
    }

    /**
     * Crée une discussion privée entre deux utilisateurs.
     * CORRECTION 2: Utilise un DTO simple "CreatePrivateChatRequest" qui correspond
     * exactement à ce que le frontend envoie ({ userId1, userId2 }).
     */
    @PostMapping("/private")
    public ResponseEntity<ChatRoomResponse> createPrivateChat(@Valid @RequestBody CreatePrivateChatRequest request) {
        ChatRoomResponse chatRoom = chatRoomService.createPrivateChatRoom(request.getUserId1(), request.getUserId2());
        return new ResponseEntity<>(chatRoom, HttpStatus.CREATED);
    }

    /**
     * Crée une discussion de groupe.
     * Utilise un DTO "CreateGroupChatRequest" qui correspond à ce que le frontend envoie.
     */
    @PostMapping("/group")
    public ResponseEntity<ChatRoomResponse> createGroupChat(@Valid @RequestBody CreateGroupChatRequest request) {
        ChatRoomResponse chatRoom = chatRoomService.createGroupChatRoom(request.getName(), request.getParticipantIds());
        return new ResponseEntity<>(chatRoom, HttpStatus.CREATED);
    }

    /**
     * Récupère toutes les discussions d'un utilisateur.
     * L'URL correspond maintenant parfaitement à l'appel frontend.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatRoomResponse>> getUserChats(@PathVariable Integer userId) {
        List<ChatRoomResponse> chatRooms = chatRoomService.getUserChatRooms(userId);
        return ResponseEntity.ok(chatRooms);
    }

    /**
     * Récupère tous les messages d'une discussion spécifique.
     * L'URL correspond maintenant parfaitement à l'appel frontend.
     */
    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Integer chatId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByChatRoomId(chatId);
        return ResponseEntity.ok(messages);
    }

    /**
     * CORRECTION 3: Ajout de l'endpoint manquant pour récupérer les messages non lus.
     * Sans cet endpoint, la fonctionnalité de notification dans la Navbar ne peut pas fonctionner.
     */
    @GetMapping("/messages/unread/{userId}")
    public ResponseEntity<List<ChatMessage>> getUnreadMessages(@PathVariable Integer userId) {
        List<ChatMessage> unreadMessages = chatMessageService.getUnreadMessagesForUser(userId);
        return ResponseEntity.ok(unreadMessages);
    }
}
