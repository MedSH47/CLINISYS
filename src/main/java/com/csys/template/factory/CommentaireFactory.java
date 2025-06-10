package com.csys.template.factory;

import com.csys.template.domain.Commentaire;
import com.csys.template.dto.CommentaireDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory for converting between Commentaire and CommentaireDTO.
 */
public class CommentaireFactory {

    public static CommentaireDTO toDTO(Commentaire commentaire) {
        if (commentaire == null) return null;

        CommentaireDTO dto = new CommentaireDTO();
        dto.setId(commentaire.getId());
        dto.setCommentaire(commentaire.getCommentaire());
        dto.setDateCommentaire(commentaire.getDateCommentaire());
        dto.setUtilisateur(UtilisateurFactory.toDTOLight(commentaire.getUtilisateur()));
        dto.setTicket(TicketFactory.toDTOLight(commentaire.getTicket()));

        return dto;
    }

    public static Commentaire toEntity(CommentaireDTO dto) {
        if (dto == null) return null;

        Commentaire entity = new Commentaire();
        entity.setId(dto.getId());
        entity.setCommentaire(dto.getCommentaire());
        entity.setDateCommentaire(dto.getDateCommentaire());
        entity.setUtilisateur(UtilisateurFactory.toEntity(dto.getUtilisateur()));
        entity.setTicket(TicketFactory.toEntity(dto.getTicket()));

        return entity;
    }

    public static CommentaireDTO toDTOLight(Commentaire commentaire) {
        if (commentaire == null) return null;

        CommentaireDTO dto = new CommentaireDTO();
        dto.setId(commentaire.getId());
        dto.setCommentaire(commentaire.getCommentaire());
        dto.setDateCommentaire(commentaire.getDateCommentaire());
        return dto;
    }

    public static List<CommentaireDTO> toDTOs(Collection<Commentaire> commentaires) {
        if (commentaires == null) return Collections.emptyList();
        return commentaires.stream().map(CommentaireFactory::toDTO).collect(Collectors.toList());
    }

    public static List<CommentaireDTO> toDTOsLight(Collection<Commentaire> commentaires) {
        if (commentaires == null) return Collections.emptyList();
        return commentaires.stream().map(CommentaireFactory::toDTOLight).collect(Collectors.toList());
    }

    public static List<Commentaire> toEntities(Collection<CommentaireDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(CommentaireFactory::toEntity).collect(Collectors.toList());
    }
}