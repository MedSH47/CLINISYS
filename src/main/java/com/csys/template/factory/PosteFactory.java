package com.csys.template.factory;

import com.csys.template.domain.Poste;
import com.csys.template.dto.PosteDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

public class PosteFactory {

  public static PosteDTO toDTO(Poste poste) {
    if (poste == null)
      return null;
    PosteDTO dto = new PosteDTO();
    dto.setId(poste.getId());
    dto.setDesignation(poste.getDesignation());
    dto.setDateCreation(poste.getDateCreation());
    dto.setUserCreation(poste.getUserCreation());
    dto.setActif(poste.isActif());
   
    return dto;
  }

  public static PosteDTO toDTOLight(Poste poste) {
    if (poste == null)
      return null;
    PosteDTO dto = new PosteDTO();
    dto.setId(poste.getId());
    dto.setDesignation(poste.getDesignation());
    return dto;
  }

  public static Poste toEntity(PosteDTO dto, Poste poste, String user) {
    if (poste == null) {
      poste = new Poste();
      poste.setId(dto.getId());

      poste.setDateCreation(LocalDateTime.now());
      poste.setUserCreation(user);
    }

    poste.setActif(dto.isActif());
    poste.setDesignation(dto.getDesignation());

    return poste;
  }


  
    public static List<PosteDTO> toDTOs(List<Poste> postes) {
        List<PosteDTO> posteDTO = new ArrayList<>();
        postes.forEach(x -> {
            posteDTO.add(toDTO(x));
        });
        return posteDTO;
    }
}