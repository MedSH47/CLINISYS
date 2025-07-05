package com.csys.template.dtoProjection;


public class ClientActivityDTO {

    private String clientName;
    private long totalTickets;
    private long openTickets; // Tickets "En Cours", "En Attente", etc.
    
    public ClientActivityDTO(String clientName, long totalTickets, long openTickets) {
        this.clientName = clientName;
        this.totalTickets = totalTickets;
        this.openTickets = openTickets;
    }

    // Getters et Setters
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public long getTotalTickets() { return totalTickets; }
    public void setTotalTickets(long totalTickets) { this.totalTickets = totalTickets; }
    public long getOpenTickets() { return openTickets; }
    public void setOpenTickets(long openTickets) { this.openTickets = openTickets; }
}