package com.csys.template.dtoProjection;


import java.time.LocalDateTime;

public class InProgressTicketDTO {
    // CORRECTION : "titre" est renommé en "name"
    private String name; 
    private LocalDateTime debutTraitement;
    private LocalDateTime date_echeance;

    // Le constructeur est mis à jour
    public InProgressTicketDTO(String name, LocalDateTime debutTraitement, LocalDateTime date_echeance) {
        this.name = name;
        this.debutTraitement = debutTraitement;
        this.date_echeance = date_echeance;
    }

    // Getters et Setters mis à jour
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getDebutTraitement() { return debutTraitement; }
    public void setDebutTraitement(LocalDateTime debutTraitement) { this.debutTraitement = debutTraitement; }
    public LocalDateTime getDateEcheance() { return date_echeance; }
    public void setDateEcheance(LocalDateTime dateEcheance) { this.date_echeance = dateEcheance; }
}