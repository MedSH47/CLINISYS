package com.csys.template.web.rest.ressource;

import com.csys.template.domain.ChatMessage;
import com.csys.template.dtoProjection.ChatContactDTO;
import com.csys.template.service.ChatMessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour exposer l'historique des conversations.
 */
@RestController
@RequestMapping("/api")
public class ChatHistoryResource {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatHistoryResource(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    /**
     * Endpoint pour récupérer l'historique de chat entre deux utilisateurs.
     * Exemple d'appel: /api/chat/history?user1=1&user2=2
     */
    @GetMapping("/chat/history")
    public List<ChatMessage> getMessages(@RequestParam Integer user1, @RequestParam Integer user2) {
        // Le service s'occupe de tout : recherche et enrichissement des données.
        return chatMessageService.findByParticipants(user1, user2);
    }
    
    /**
     * Endpoint pour récupérer la liste des dernières conversations d'un utilisateur.
     * Exemple d'appel: /api/chat/my-messages/1
     */
    @GetMapping("/chat/my-messages/{userId}")
    public List<ChatContactDTO> getMyMessages(@PathVariable Integer userId) {
        return chatMessageService.findMyChatMessages(userId);
    }
}