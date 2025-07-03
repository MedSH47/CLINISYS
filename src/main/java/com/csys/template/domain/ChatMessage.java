package com.csys.template.domain;


import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.envers.Audited;

import com.csys.template.domain.enum_identifier.MessageType;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChatMessage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
  @Audited 

public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sender_id")
    private Integer sender;

    @Column(length = 2000, name = "message_content")
    private String content;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "receiver_id")
    private Integer receiver;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // CHANGE annee fields to use the DTO
    @Transient
    @JsonProperty("senderDetails")
    private UtilisateurResponseDTO senderDetails;

    @Transient
    @JsonProperty("receiverDetails")
    private UtilisateurResponseDTO receiverDetails;
    
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
    
    
}
