package com.csys.template.dtoResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentaireResponseDTO {

    private Integer id;
    private String commentaire;
    private LocalDateTime dateCommentaire;

    // Using "light" DTOs for nested responses
    private UtilisateurResponseDTO utilisateur;
    private TicketResponseDTO ticket;

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

    public LocalDateTime getDateCommentaire() {
        return dateCommentaire;
    }

    public void setDateCommentaire(LocalDateTime dateCommentaire) {
        this.dateCommentaire = dateCommentaire;
    }

    public UtilisateurResponseDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurResponseDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public TicketResponseDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketResponseDTO ticket) {
        this.ticket = ticket;
    }
}