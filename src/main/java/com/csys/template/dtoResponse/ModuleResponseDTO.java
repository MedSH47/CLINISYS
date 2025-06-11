package com.csys.template.dtoResponse;

import java.time.LocalDateTime;
import java.util.Collection;

public class ModuleResponseDTO {
  private Integer id;
  private String designation;
  private LocalDateTime dateCreation;
  private String userCreation;

  // These will be populated with "light" DTOs
  private EquipeResponseDTO equipe;
  private Collection<TicketResponseDTO> ticketList;

  // Getters and Setters

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
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

  public EquipeResponseDTO getEquipe() {
    return equipe;
  }

  public void setEquipe(EquipeResponseDTO equipe) {
    this.equipe = equipe;
  }

  public Collection<TicketResponseDTO> getTicketList() {
    return ticketList;
  }

  public void setTicketList(Collection<TicketResponseDTO> ticketList) {
    this.ticketList = ticketList;
  }
}