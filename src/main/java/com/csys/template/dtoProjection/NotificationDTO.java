package com.csys.template.dtoProjection;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationDTO {

    private String id;
    private String message;
    private LocalDateTime timestamp;
    private String type; // ex: 'TICKET_UPDATE'

    public NotificationDTO(String message, String type) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }
     public NotificationDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getters et Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
