package com.csys.template.factory;

import com.csys.template.domain.Poste;
import com.csys.template.dto.PosteDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosteFactory {

    public static PosteDTO toDTO(Poste poste) {
        if (poste == null) return null;
        PosteDTO dto = new PosteDTO();
        dto.setId(poste.getId());
        dto.setDesignation(poste.getDesignation());
        dto.setDateCreation(poste.getDateCreation());
        dto.setUserCreation(poste.getUserCreation());
        if (poste.getEquipePosteutilisateurSet() != null) {
            dto.setEquipePosteutilisateurSet(EquipePosteutilisateurFactory.toDTOs(poste.getEquipePosteutilisateurSet()));
        }
        return dto;
    }

    public static PosteDTO toDTOLight(Poste poste) {
        if (poste == null) return null;
        PosteDTO dto = new PosteDTO();
        dto.setId(poste.getId());
        dto.setDesignation(poste.getDesignation());
        return dto;
    }

    public static Poste toEntity(PosteDTO dto) {
        if (dto == null) return null;
        Poste entity = new Poste();
        entity.setId(dto.getId());
        entity.setDesignation(dto.getDesignation());
        entity.setDateCreation(dto.getDateCreation());
        entity.setUserCreation(dto.getUserCreation());
        return entity;
    }
    
    public static List<PosteDTO> toDTOs(Collection<Poste> postes) {
        if (postes == null) return Collections.emptyList();
        return postes.stream().map(PosteFactory::toDTOLight).collect(Collectors.toList());
    }

    public static List<Poste> toEntities(Collection<PosteDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(PosteFactory::toEntity).collect(Collectors.toList());
    }
}