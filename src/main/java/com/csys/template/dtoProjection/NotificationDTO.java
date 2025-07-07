package com.csys.template.dtoProjection;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private String id;
    private String message;
    private String link;
    private LocalDateTime timestamp;
    private String type; // ex: 'TICKET_UPDATE'

    // ✅ Constructeur personnalisé depuis un Ticket
    public NotificationDTO(com.csys.template.domain.Ticket ticket, String message, String type) {
        this.id = UUID.randomUUID().toString(); // pour les WebSocket, utile côté client
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.link = "/tickets/" + ticket.getId(); // ou toute URL front correspondante
    }
    public NotificationDTO(String message, String type) {
    this.id = UUID.randomUUID().toString();
    this.message = message;
    this.type = type;
    this.timestamp = LocalDateTime.now();
    this.link = null; // ou "" si tu veux
}

}
