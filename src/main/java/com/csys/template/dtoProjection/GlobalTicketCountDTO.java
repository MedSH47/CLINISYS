// Dans un nouveau fichier: src/main/java/com/csys/template/dtoProjection/GlobalTicketCountDTO.java
package com.csys.template.dtoProjection;

public class GlobalTicketCountDTO {
    private Long totalTickets;
    private Long ticketsEnAttente;
    private Long ticketsEnCours;
    private Long ticketsAcceptes;
    private Long ticketsRefuses;
    private Long ticketsTerminesToday;
    private Long ticketsTerminesThisWeek;

    // Constructeur complet (peut être généré par un builder ou Lombok)
    public GlobalTicketCountDTO(Long totalTickets, Long ticketsEnAttente, Long ticketsEnCours, Long ticketsAcceptes,
                                Long ticketsRefuses, Long ticketsTerminesToday, Long ticketsTerminesThisWeek) {
        this.totalTickets = totalTickets;
        this.ticketsEnAttente = ticketsEnAttente;
        this.ticketsEnCours = ticketsEnCours;
        this.ticketsAcceptes = ticketsAcceptes;
        this.ticketsRefuses = ticketsRefuses;
        this.ticketsTerminesToday = ticketsTerminesToday;
        this.ticketsTerminesThisWeek = ticketsTerminesThisWeek;
    }

    // Getters
    public Long getTotalTickets() { return totalTickets; }
    public Long getTicketsEnAttente() { return ticketsEnAttente; }
    public Long getTicketsEnCours() { return ticketsEnCours; }
    public Long getTicketsAcceptes() { return ticketsAcceptes; }
    public Long getTicketsRefuses() { return ticketsRefuses; }
    public Long getTicketsTerminesToday() { return ticketsTerminesToday; }
    public Long getTicketsTerminesThisWeek() { return ticketsTerminesThisWeek; }

    // Setters (omis pour concision)
}