package com.csys.template.dtoResponse;




import java.time.LocalDateTime;
import java.util.Set;

import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.ChatType;

public class ChatRoomResponse {
    private Integer id;
    private ChatType type;
    private String name;
    private LocalDateTime createdAt;
    private String lastMessageContent;
    private LocalDateTime lastMessageTimestamp;
    private Set<Utilisateur> participants; // Informations complètes des participants
    public ChatRoomResponse() {
    }
    public ChatRoomResponse(Integer id, ChatType type, String name, LocalDateTime createdAt, String lastMessageContent,
            LocalDateTime lastMessageTimestamp, Set<Utilisateur> participants) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.createdAt = createdAt;
        this.lastMessageContent = lastMessageContent;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.participants = participants;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public ChatType getType() {
        return type;
    }
    public void setType(ChatType type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getLastMessageContent() {
        return lastMessageContent;
    }
    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }
    public LocalDateTime getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }
    public void setLastMessageTimestamp(LocalDateTime lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }
    public Set<Utilisateur> getParticipants() {
        return participants;
    }
    public void setParticipants(Set<Utilisateur> participants) {
        this.participants = participants;
    }

    // Optionnel: constructeur pour mapper depuis l'entité ChatRoom
}