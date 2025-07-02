// Dans un nouveau fichier: src/main/java/com/csys/template/dtoProjection/ActiveTicketCountDTO.java
package com.csys.template.dtoProjection;

public class ActiveTicketCountDTO {
    private String category; // Nom de l'employé ou du module
    private Long activeTickets;

    public ActiveTicketCountDTO(String category, Long activeTickets) {
        this.category = category;
        this.activeTickets = activeTickets;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getActiveTickets() { return activeTickets; }
    public void setActiveTickets(Long activeTickets) { this.activeTickets = activeTickets; }
}