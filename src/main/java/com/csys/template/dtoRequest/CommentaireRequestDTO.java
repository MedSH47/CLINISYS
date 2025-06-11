package com.csys.template.dtoRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentaireRequestDTO {

    @NotNull
    @Size(min = 1, max = 2147483647)
    private String commentaire;

    @NotNull
    private Integer idTicket;

    @NotNull
    private Integer idUtilisateur;

    // Getters and Setters
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

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}