/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Ticket")

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "num_ticket")
    private Integer numTicket;
  
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    
    private Status status;
   
    @Column(name = "priorite")
    @Enumerated(EnumType.STRING)
    private Priorite priorite;
    @Column(name = "date_effectation_equip")
    @Temporal(TemporalType.DATE)
    private Date dateEffectationEquip;
    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
   
    @Column(name = "creation_user")
    private String creationUser;
   
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "collaborateur", referencedColumnName = "id")
    private Utilisateur collaborateur;
   
    @Column(name = "echeance")
    private String echeance;
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @ManyToOne
    private Client idClient;
    @JoinColumn(name = "id_equip", referencedColumnName = "id")
    @ManyToOne
    private Equipe idEquip;
    @JoinColumn(name = "id_module", referencedColumnName = "id")
    @ManyToOne
    private Module idModule;

    @Column(name = "designation")
    private String designation;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Ticket(Integer id, Integer numTicket, Status status,
             Priorite priorite, Date dateEffectationEquip, Date dateCreation,
            String creationUser, Utilisateur collaborateur, String echeance,
            Client idClient, Equipe idEquip, Module idModule,String designation) {
        this.id = id;
        this.numTicket = numTicket;
        this.status = status;
        this.priorite = priorite;
        this.dateEffectationEquip = dateEffectationEquip;
        this.dateCreation = dateCreation;
        this.creationUser = creationUser;
        this.collaborateur = collaborateur;
        this.echeance = echeance;
        this.idClient = idClient;
        this.idEquip = idEquip;
        this.idModule = idModule;
        this.designation=designation;
    }

    public Ticket() {
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(Integer numTicket) {
        this.numTicket = numTicket;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public Date getDateEffectationEquip() {
        return dateEffectationEquip;
    }

    public void setDateEffectationEquip(Date dateEffectationEquip) {
        this.dateEffectationEquip = dateEffectationEquip;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public Utilisateur getCollaborateur() {
        return collaborateur;
    }

    public void setCollaborateur(Utilisateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public String getEcheance() {
        return echeance;
    }

    public void setEcheance(String echeance) {
        this.echeance = echeance;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Equipe getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Equipe idEquip) {
        this.idEquip = idEquip;
    }

    public Module getIdModule() {
        return idModule;
    }

    public void setIdModule(Module idModule) {
        this.idModule = idModule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.domain.Ticket[ id=" + id + " ]";
    }
    
}
