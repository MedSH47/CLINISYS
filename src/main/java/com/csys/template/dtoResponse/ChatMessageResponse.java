package com.csys.template.dtoResponse;

import java.time.LocalDateTime;
import java.util.Set;

import com.csys.template.domain.ChatRoom;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.ChatType;

public class ChatMessageResponse {

    private Integer id;
    private String content;
    private LocalDateTime timestamp;
    private Utilisateur sender;
    private ChatRoom chatRoom;

    public ChatMessageResponse(Integer id, String content, LocalDateTime timestamp, Utilisateur sender,
            ChatRoom chatRoom) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
        this.chatRoom = chatRoom;
    }

    public ChatMessageResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Utilisateur getSender() {
        return sender;
    }

    public void setSender(Utilisateur sender) {
        this.sender = sender;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

}
