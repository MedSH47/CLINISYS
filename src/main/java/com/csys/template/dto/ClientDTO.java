package com.csys.template.dto;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientDTO {
  @NotNull
  private Integer id;

  @Size(
      min = 0,
      max = 100
  )
  private String nomComplet;

  @Size(
      min = 0,
      max = 200
  )
  private String adress;

  @Size(
      min = 0,
      max = 100
  )
  private String email;

  @Size(
      min = 0,
      max = 50
  )
  private String region;

  private Boolean actif;

  private Collection<TicketDTO> ticketCollection;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNomComplet() {
    return nomComplet;
  }

  public void setNomComplet(String nomComplet) {
    this.nomComplet = nomComplet;
  }

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public Boolean getActif() {
    return actif;
  }

  public void setActif(Boolean actif) {
    this.actif = actif;
  }

  public Collection<TicketDTO> getTicketCollection() {
    return ticketCollection;
  }

  public void setTicketCollection(Collection<TicketDTO> ticketCollection) {
    this.ticketCollection = ticketCollection;
  }
}

