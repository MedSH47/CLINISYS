package com.csys.template.dtoRequest;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicketRequestDTO {

    @NotNull
    @Size(min = 1, max = 200)
    private String titre;

    @Size(max = 2147483647)
    private String description;

    private Integer idParentTicket;

    
    private Priorite priorite;

    @NotNull
    private Status statue;
    
    @NotNull
    private Integer idClient;

    @NotNull
    private Integer idModule;

    private Integer idUtilisateur; // Can be nullable if a ticket can be unassigned

    // Getters and Setters

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