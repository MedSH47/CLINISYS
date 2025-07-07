// Fichier à modifier : src/main/java/com/csys/template/domain/ChatMessage.java

package com.csys.template.domain;

import com.csys.template.domain.enum_identifier.MessageType;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore; // ✅ IMPORT NÉCESSAIRE
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "ChatMessage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Audited
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sender_id")
    private Integer sender;

    @Column(length = 2000, name = "message_content")
    private String content; // Pour les fichiers, contiendra un placeholder comme "[Fichier]"

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;
    
    // ✅ NOUVEAU CHAMP POUR LE CONTENU DU FICHIER
    @Lob // Indique à JPA de stocker ceci comme un Large Object (BLOB)
    @Basic(fetch = FetchType.LAZY) // Se charge uniquement lorsqu'on y accède explicitement
    @JsonIgnore // N'inclut jamais ce champ dans les réponses JSON (trop lourd)
    @Column(name = "file_content")
    private byte[] fileContent;

    @Column(name = "receiver_id")
    private Integer receiver;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

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