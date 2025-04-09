package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EquipeDTO {
  @NotNull
  private Integer id;

  @Size(
      min = 0,
      max = 30
  )
  private String nomEquipe;

  @Temporal(TemporalType.DATE)
  private Date creationDate;

  @Size(
      min = 0,
      max = 30
  )
  private String creationUser;

  private List ticketList;

  private List utilisateurList;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNomEquipe() {
    return nomEquipe;
  }

  public void setNomEquipe(String nomEquipe) {
    this.nomEquipe = nomEquipe;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public String getCreationUser() {
    return creationUser;
  }

  public void setCreationUser(String creationUser) {
    this.creationUser = creationUser;
  }

  public List getTicketList() {
    return ticketList;
  }

  public void setTicketList(List ticketList) {
    this.ticketList = ticketList;
  }

  public List getUtilisateurList() {
    return utilisateurList;
  }

  public void setUtilisateurList(List utilisateurList) {
    this.utilisateurList = utilisateurList;
  }
}

