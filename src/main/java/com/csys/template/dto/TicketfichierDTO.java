package com.csys.template.dto;

import com.csys.template.domain.Fichiersjointes;
import com.csys.template.domain.Ticket;
import java.lang.Integer;
import javax.validation.constraints.NotNull;

public class TicketfichierDTO {
  @NotNull
  private Integer id;

  private Fichiersjointes idFichier;

  private Ticket idTicket;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Fichiersjointes getIdFichier() {
    return idFichier;
  }

  public void setIdFichier(Fichiersjointes idFichier) {
    this.idFichier = idFichier;
  }

  public Ticket getIdTicket() {
    return idTicket;
  }

  public void setIdTicket(Ticket idTicket) {
    this.idTicket = idTicket;
  }
}

