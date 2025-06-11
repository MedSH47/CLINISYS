package com.csys.template.factory;

import com.csys.template.domain.Poste;
import com.csys.template.dtoRequest.PosteRequestDTO;
import com.csys.template.dtoResponse.PosteResponseDTO;
import com.csys.template.util.Helper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosteFactory {

    public static PosteResponseDTO toResponseDTO(Poste poste) {
        if (poste == null) return null;
        PosteResponseDTO dto = new PosteResponseDTO();
        dto.setId(poste.getId());
        dto.setDesignation(poste.getDesignation());
        dto.setActif(poste.isActif());
        dto.setDateCreation(poste.getDateCreation());
        dto.setUserCreation(poste.getUserCreation());
        return dto;
    }

    public static PosteResponseDTO toDTOLight(Poste poste) {
        if (poste == null) return null;
        PosteResponseDTO dto = new PosteResponseDTO();
        dto.setId(poste.getId());
        dto.setDesignation(poste.getDesignation());
        return dto;
    }

    public static Poste toEntity(PosteRequestDTO dto) {
        if (dto == null) return null;
        Poste entity = new Poste();
        entity.setDesignation(dto.getDesignation());
        entity.setActif(dto.isActif());
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated());
        return entity;
    }

    public static List<PosteResponseDTO> toResponseDTOs(List<Poste> postes) {
        if (postes == null) return Collections.emptyList();
        return postes.stream().map(PosteFactory::toResponseDTO).collect(Collectors.toList());
    }
}