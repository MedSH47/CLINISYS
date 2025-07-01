package com.csys.template.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Utilisateur sender;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // --- AJOUT IMPORTANT ---
    // Cette relation ManyToMany crée une table de jointure (ex: chat_message_read_by)
    // pour suivre exactement quel utilisateur a lu quel message.
    // C'est la base pour savoir si un message est "non lu" pour quelqu'un.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "chat_message_read_receipts",
        joinColumns = @JoinColumn(name = "chat_message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore // On ignore ce champ lors de la sérialisation pour éviter les boucles infinies.
    private Set<Utilisateur> readByUsers = new HashSet<>();
    
    // Getters, setters, etc.
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Utilisateur getSender() {
        return sender;
    }

    public void setSender(Utilisateur sender) {
        this.sender = sender;
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

    public Set<Utilisateur> getReadByUsers() {
        return readByUsers;
    }

    public void setReadByUsers(Set<Utilisateur> readByUsers) {
        this.readByUsers = readByUsers;
    }
}
