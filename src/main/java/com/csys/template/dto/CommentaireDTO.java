package com.csys.template.dto;

import com.csys.template.domain.Avancement;
import java.lang.Integer;
import java.lang.String;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentaireDTO {
  @NotNull
  private Integer id;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String commentaire;

  private Integer idTicket;

  private Avancement idAvancement;

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

  public Integer getIdTicket() {
    return idTicket;
  }

  public void setIdTicket(Integer idTicket) {
    this.idTicket = idTicket;
  }

  public Avancement getIdAvancement() {
    return idAvancement;
  }

  public void setIdAvancement(Avancement idAvancement) {
    this.idAvancement = idAvancement;
  }
}

