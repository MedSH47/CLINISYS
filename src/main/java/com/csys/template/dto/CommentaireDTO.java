package com.csys.template.dto;

import com.csys.template.domain.Ticket;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

public class CommentaireDTO {
  private Integer id;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String commentaire;

  @Temporal(TemporalType.DATE)
  private Date dateCommentaire;

  private Ticket idTicket;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCommentaire() {
    return commentaire;
  }

  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

  public Date getDateCommentaire() {
    return dateCommentaire;
  }

  public void setDateCommentaire(Date dateCommentaire) {
    this.dateCommentaire = dateCommentaire;
  }

  public Ticket getIdTicket() {
    return idTicket;
  }

  public void setIdTicket(Ticket idTicket) {
    this.idTicket = idTicket;
  }
}

