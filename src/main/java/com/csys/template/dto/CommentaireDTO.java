package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentaireDTO {
  private Integer id;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String commentaire;

  private UtilisateurDTO utilisateur;



  private LocalDateTime dateCommentaire;

  private TicketDTO ticket;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public UtilisateurDTO getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(UtilisateurDTO utilisateur) {
    this.utilisateur = utilisateur;
  }

  public String getCommentaire() {
    return commentaire;
  }

  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

  public LocalDateTime getDateCommentaire() {
    return dateCommentaire;
  }

  public void setDateCommentaire(LocalDateTime dateCommentaire) {
    this.dateCommentaire = dateCommentaire;
  }

  public TicketDTO getTicket() {
    return ticket;
  }

  public void setTicket(TicketDTO ticket) {
    this.ticket = ticket;
  }
}

