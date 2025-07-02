// Dans un nouveau fichier: src/main/java/com/csys/template/dtoProjection/TicketCalendarEventDTO.java
package com.csys.template.dtoProjection;

import java.time.LocalDateTime;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;

public class TicketCalendarEventDTO {
    private Integer id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean allDay;
    private String color;
    private String ticketStatus;
    private String ticketPriority;
    private String assignedTo;

    // Constructeur pour projection QueryDSL
    public TicketCalendarEventDTO(Integer id, String titre, LocalDateTime dateEcheance, Priorite priorite, Status status, String assignedToName) {
        this.id = id;
        this.title = "Échéance Ticket #" + id + ": " + titre;
        this.start = dateEcheance;
        this.end = dateEcheance != null ? dateEcheance.plusHours(1) : null;
        this.allDay = false;
        this.ticketStatus = status != null ? status.toString() : null;
        this.ticketPriority = priorite != null ? priorite.toString() : null;
        this.assignedTo = assignedToName;
        setColorBasedOnPriority(priorite);
    }

    // Setter de couleur interne pour simplifier le constructeur
    private void setColorBasedOnPriority(Priorite priorite) {
        if (priorite == Priorite.Haute) {
            this.color = "#FF6347"; // Tomato (reddish)
        } else if (priorite == Priorite.Moyenne) {
            this.color = "#FFD700"; // Gold (yellowish)
        } else if (priorite == Priorite.Basse) {
            this.color = "#90EE90"; // LightGreen
        } else {
            this.color = "#ADD8E6"; // Default light blue
        }
    }

    // Getters et Setters pour toutes les propriétés (omis pour concision ici, mais nécessaires)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getStart() { return start; }
    public void setStart(LocalDateTime start) { this.start = start; }
    public LocalDateTime getEnd() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; }
    public boolean isAllDay() { return allDay; }
    public void setAllDay(boolean allDay) { this.allDay = allDay; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getTicketStatus() { return ticketStatus; }
    public void setTicketStatus(String ticketStatus) { this.ticketStatus = ticketStatus; }
    public String getTicketPriority() { return ticketPriority; }
    public void setTicketPriority(String ticketPriority) { this.ticketPriority = ticketPriority; }
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
}