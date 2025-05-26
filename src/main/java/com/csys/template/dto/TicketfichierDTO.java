package com.csys.template.dto;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.domain.Ticket;
import java.lang.Integer;

public class TicketfichierDTO {
  private Integer id;

  private DocumentJointes idFichier;

  private Ticket idTicket;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DocumentJointes getIdFichier() {
    return idFichier;
  }

  public void setIdFichier(DocumentJointes idFichier) {
    this.idFichier = idFichier;
  }

  public Ticket getIdTicket() {
    return idTicket;
  }

  public void setIdTicket(Ticket idTicket) {
    this.idTicket = idTicket;
  }
}

