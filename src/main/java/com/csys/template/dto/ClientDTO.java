package com.csys.template.dto;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientDTO {
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

  private LocalDateTime dateCreation;

  @Size(
      min = 0,
      max = 50
  )
  private String userCreation;

  private Boolean actif;

  private List<TicketDTO> ticketList;

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

  public LocalDateTime getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
    this.dateCreation = dateCreation;
  }

  public String getUserCreation() {
    return userCreation;
  }

  public void setUserCreation(String userCreation) {
    this.userCreation = userCreation;
  }

  public Boolean getActif() {
    return actif;
  }

  public void setActif(Boolean actif) {
    this.actif = actif;
  }

  public List<TicketDTO> getTicketList() {
    return ticketList;
  }

  public void setTicketList(List<TicketDTO> ticketList) {
    this.ticketList = ticketList;
  }
}

