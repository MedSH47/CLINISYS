package com.csys.template.domain;


import javax.persistence.*;

import com.csys.template.domain.enum_identifier.MessageType;

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
    
    
}
