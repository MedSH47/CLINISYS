// package com.csys.template.domain;

// import java.time.LocalDateTime;
// import java.util.HashSet;
// import java.util.Set;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.EntityListeners;
// import javax.persistence.EnumType;
// import javax.persistence.Enumerated;
// import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.JoinTable;
// import javax.persistence.ManyToMany;
// import javax.persistence.Table;

// import com.csys.template.domain.enum_identifier.ChatType;
// import com.csys.template.log.listener.EntityLogger;


// @Entity
// @Table(name = "chat_rooms")
// @EntityListeners(EntityLogger.class)
// public class ChatRoom {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;

//     @Column( name = "type")
//     @Enumerated(EnumType.STRING)
//     private ChatType type; // PRIVATE, GROUP

//     @Column(name = "name")
//     private String name; // Name for group chats

//     @Column(nullable = false, name = "created_at")
//     private LocalDateTime createdAt;

//     @Column(length = 500,name= "last_message_content")
//     private String lastMessageContent;

//     @Column(name = "last_message_timestamp")
//     private LocalDateTime lastMessageTimestamp;

//     // Eager loading for simplicity, consider LAZY loading and separate queries for performance in large apps
//     @ManyToMany(fetch = FetchType.EAGER)
//     @JoinTable(
//         name = "chat_room_participants",
//         joinColumns = @JoinColumn(name = "chat_room_id"),
//         inverseJoinColumns = @JoinColumn(name = "user_id")
//     )
//     private Set<Utilisateur> participants = new HashSet<>();

//     public ChatRoom(Integer id, ChatType type, String name, LocalDateTime createdAt, String lastMessageContent,
//             LocalDateTime lastMessageTimestamp, Set<Utilisateur> participants) {
//         this.id = id;
//         this.type = type;
//         this.name = name;
//         this.createdAt = createdAt;
//         this.lastMessageContent = lastMessageContent;
//         this.lastMessageTimestamp = lastMessageTimestamp;
//         this.participants = participants;
//     }

//     public ChatRoom() {
//     }

//     public ChatRoom(ChatType type, String name) {
//         this.type = type;
//         this.name = name;
//         this.createdAt = LocalDateTime.now();
//     }

//     public Integer getId() {
//         return id;
//     }

//     public void setId(Integer id) {
//         this.id = id;
//     }

//     public ChatType getType() {
//         return type;
//     }

//     public void setType(ChatType type) {
//         this.type = type;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public LocalDateTime getCreatedAt() {
//         return createdAt;
//     }

//     public void setCreatedAt(LocalDateTime createdAt) {
//         this.createdAt = createdAt;
//     }

//     public String getLastMessageContent() {
//         return lastMessageContent;
//     }

//     public void setLastMessageContent(String lastMessageContent) {
//         this.lastMessageContent = lastMessageContent;
//     }

//     public LocalDateTime getLastMessageTimestamp() {
//         return lastMessageTimestamp;
//     }

//     public void setLastMessageTimestamp(LocalDateTime lastMessageTimestamp) {
//         this.lastMessageTimestamp = lastMessageTimestamp;
//     }

//     public Set<Utilisateur> getParticipants() {
//         return participants;
//     }

//     public void setParticipants(Set<Utilisateur> participants) {
//         this.participants = participants;
//     }
// }