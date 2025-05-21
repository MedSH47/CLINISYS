package com.csys.template.dto;

import com.csys.template.domain.Client;
import com.csys.template.domain.Module;
import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicketDTO {
  @NotNull
  private Integer id;

  private Integer idTicketParent;

  @Size(
      min = 0,
      max = 200
  )
  private String titre;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String description;

  @Size(
      min = 0,
      max = 50
  )
  private String userCreation;

  @Temporal(TemporalType.DATE)
  private Date dateCreation;

  @Size(
      min = 0,
      max = 20
  )
  private String statue;

  private Set<AvancementDTO> avancementCollection;

  private Client idClient;

  private Module idModule;

  private Set<TicketfichierDTO> ticketfichierCollection;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIdTicketParent() {
    return idTicketParent;
  }

  public void setIdTicketParent(Integer idTicketParent) {
    this.idTicketParent = idTicketParent;
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

  public String getUserCreation() {
    return userCreation;
  }

  public void setUserCreation(String userCreation) {
    this.userCreation = userCreation;
  }

  public Date getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(Date dateCreation) {
    this.dateCreation = dateCreation;
  }

  public String getStatue() {
    return statue;
  }

  public void setStatue(String statue) {
    this.statue = statue;
  }

  public Set<AvancementDTO> getAvancementCollection() {
    return avancementCollection;
  }

  public void setAvancementCollection(Set<AvancementDTO> avancementCollection) {
    this.avancementCollection = avancementCollection;
  }

  public Client getIdClient() {
    return idClient;
  }

  public void setIdClient(Client idClient) {
    this.idClient = idClient;
  }

  public Module getIdModule() {
    return idModule;
  }

  public void setIdModule(Module idModule) {
    this.idModule = idModule;
  }

  public Set<TicketfichierDTO> getTicketfichierCollection() {
    return ticketfichierCollection;
  }

  public void setTicketfichierCollection(Set<TicketfichierDTO> ticketfichierCollection) {
    this.ticketfichierCollection = ticketfichierCollection;
  }
}

