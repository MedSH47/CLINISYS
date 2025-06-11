package com.csys.template.factory;

import com.csys.template.domain.Commentaire;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoRequest.CommentaireRequestDTO;
import com.csys.template.dtoResponse.CommentaireResponseDTO;
import com.csys.template.util.Helper;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommentaireFactory {

    public static CommentaireResponseDTO toResponseDTO(Commentaire commentaire) {
        if (commentaire == null) return null;

        CommentaireResponseDTO dto = new CommentaireResponseDTO();
        dto.setId(commentaire.getId());
        dto.setCommentaire(commentaire.getCommentaire());
        dto.setDateCommentaire(commentaire.getDateCommentaire());
        dto.setUtilisateur(UtilisateurFactory.toDTOLight(commentaire.getUtilisateur()));
        dto.setTicket(TicketFactory.toDTOLight(commentaire.getTicket()));

        return dto;
    }

    public static Commentaire toEntity(CommentaireRequestDTO dto) {
        if (dto == null) return null;

        Commentaire entity = new Commentaire();
        entity.setCommentaire(dto.getCommentaire());
        entity.setDateCommentaire(LocalDateTime.now());

        if (dto.getIdTicket() != null) {
            Ticket ticket = new Ticket();
            ticket.setId(dto.getIdTicket());
            entity.setTicket(ticket);
        }

        if (dto.getIdUtilisateur() != null) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(dto.getIdUtilisateur());
            entity.setUtilisateur(utilisateur);
        }
        return entity;
    }

    public static List<CommentaireResponseDTO> toResponseDTOs(Collection<Commentaire> commentaires) {
        if (commentaires == null) return Collections.emptyList();
        return commentaires.stream().map(CommentaireFactory::toResponseDTO).collect(Collectors.toList());
    }
}