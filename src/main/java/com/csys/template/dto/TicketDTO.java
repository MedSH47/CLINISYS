package com.csys.template.dto;

import com.csys.template.domain.Client;
import com.csys.template.domain.Equipe;
import com.csys.template.domain.Module;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class TicketDTO {
 
  private Integer id;

  private Integer numTicket;

  private Status status;


  private Priorite priorite;

  @Temporal(TemporalType.DATE)
  private Date dateEffectationEquip;

  @Temporal(TemporalType.DATE)
  private Date dateCreation;

 
  private String creationUser;


  private String collaborateur;

  private String echeance;

  private Client idClient;

  private Equipe idEquip;

  private Module idModule;

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

  public String getCollaborateur() {
    return collaborateur;
  }

  public void setCollaborateur(String collaborateur) {
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
}

