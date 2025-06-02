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
        dto.setIdTicket(commentaire.getIdTicket());

        return dto;
    }

    public static Commentaire toEntity(CommentaireDTO dto) {
        if (dto == null) return null;

        Commentaire entity = new Commentaire();
        entity.setId(dto.getId());
        entity.setCommentaire(dto.getCommentaire());
        entity.setDateCommentaire(dto.getDateCommentaire());
        entity.setIdTicket(dto.getIdTicket());

        return entity;
    }

    public static List<CommentaireDTO> toDTOs(Collection<Commentaire> commentaires) {
        if (commentaires == null) return Collections.emptyList();
        return commentaires.stream().map(CommentaireFactory::toDTO).collect(Collectors.toList());
    }

    public static List<Commentaire> toEntities(Collection<CommentaireDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(CommentaireFactory::toEntity).collect(Collectors.toList());
    }
}