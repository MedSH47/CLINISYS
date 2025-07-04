package com.csys.template.dtoRequest;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;


import java.time.LocalDateTime;

import javax.validation.constraints.Size;

public class TicketRequestDTO {

    @Size(min = 1, max = 200)
    private String titre;

    @Size(max = 2147483647)
    private String description;

    private Integer idParentTicket;
    
    private Priorite priorite;

    private Status statue;

    private Integer idClient;

    private LocalDateTime debutTraitement;

    private Integer idModule;

    private Integer idUtilisateur;      

    private Boolean actif;

    private LocalDateTime date_echeance;
    
    private LocalDateTime dateCloture;

    public LocalDateTime getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }
    public LocalDateTime getDebutTraitement() {
        return debutTraitement;
    }
    public void setDebutTraitement(LocalDateTime debutTraitement) {
        this.debutTraitement = debutTraitement;
    }

    // Getters and Setters

    public LocalDateTime getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(LocalDateTime date_echeance) {
        this.date_echeance = date_echeance;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdParentTicket() {
        return idParentTicket;
    }

    public void setIdParentTicket(Integer idParentTicket) {
        this.idParentTicket = idParentTicket;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public Status getStatue() {
        return statue;
    }

    public void setStatue(Status statue) {
        this.statue = statue;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}