package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.validation.constraints.Size;

public class ModuleDTO {
  private Integer id;

  private LocalDateTime dateCreation;

  @Size(
      min = 0,
      max = 50
  )
  private String userCreation;

  @Size(
      min = 0,
      max = 100
  )
  private String designation;

  private Collection<TicketDTO> ticketSet;
  
  private EquipeDTO equipe;

  public EquipeDTO getEquipe() {
    return equipe;
  }

  public void setEquipe(EquipeDTO equipe) {
    this.equipe = equipe;
  }

 

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public Collection<TicketDTO> getTicketSet() {
    return ticketSet;
  }

  public void setTicketSet(Collection<TicketDTO> ticketSet) {
    this.ticketSet = ticketSet;
  }

 
}

