package com.csys.template.dto;

import com.csys.template.domain.Equipe;
import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ModuleDTO {
  @NotNull
  private Integer id;

  @Size(
      min = 0,
      max = 100
  )
  private String designation;

  private Collection<TicketDTO> ticketCollection;

  private Equipe idEquipe;

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

  public Collection<TicketDTO> getTicketCollection() {
    return ticketCollection;
  }

  public void setTicketCollection(Collection<TicketDTO> ticketCollection) {
    this.ticketCollection = ticketCollection;
  }

  public Equipe getIdEquipe() {
    return idEquipe;
  }

  public void setIdEquipe(Equipe idEquipe) {
    this.idEquipe = idEquipe;
  }
}

