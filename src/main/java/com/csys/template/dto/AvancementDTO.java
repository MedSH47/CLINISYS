package com.csys.template.dto;

import com.csys.template.domain.Ticket;
import java.lang.Integer;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

public class AvancementDTO {
  @NotNull
  private Integer id;

  @Temporal(TemporalType.DATE)
  private Date dateEcheance;

  @Temporal(TemporalType.DATE)
  private Date dateDebut;

  @Temporal(TemporalType.DATE)
  private Date dateFin;

  @Temporal(TemporalType.TIME)
  private Date dureeTravail;

  private Ticket idTicket;

  private Collection<CommentaireDTO> commentaireCollection;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getDateEcheance() {
    return dateEcheance;
  }

  public void setDateEcheance(Date dateEcheance) {
    this.dateEcheance = dateEcheance;
  }

  public Date getDateDebut() {
    return dateDebut;
  }

  public void setDateDebut(Date dateDebut) {
    this.dateDebut = dateDebut;
  }

  public Date getDateFin() {
    return dateFin;
  }

  public void setDateFin(Date dateFin) {
    this.dateFin = dateFin;
  }

  public Date getDureeTravail() {
    return dureeTravail;
  }

  public void setDureeTravail(Date dureeTravail) {
    this.dureeTravail = dureeTravail;
  }

  public Ticket getIdTicket() {
    return idTicket;
  }

  public void setIdTicket(Ticket idTicket) {
    this.idTicket = idTicket;
  }

  public Collection<CommentaireDTO> getCommentaireCollection() {
    return commentaireCollection;
  }

  public void setCommentaireCollection(Collection<CommentaireDTO> commentaireCollection) {
    this.commentaireCollection = commentaireCollection;
  }
}

