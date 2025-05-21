package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FichiersjointesDTO {
  @NotNull
  private Integer id;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String numFichier;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String designation;

  private Collection<TicketfichierDTO> ticketfichierCollection;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNumFichier() {
    return numFichier;
  }

  public void setNumFichier(String numFichier) {
    this.numFichier = numFichier;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public Collection<TicketfichierDTO> getTicketfichierCollection() {
    return ticketfichierCollection;
  }

  public void setTicketfichierCollection(Collection<TicketfichierDTO> ticketfichierCollection) {
    this.ticketfichierCollection = ticketfichierCollection;
  }
}

