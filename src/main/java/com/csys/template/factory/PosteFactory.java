package com.csys.template.factory;

import com.csys.template.domain.Poste;
import com.csys.template.dto.PosteDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PosteFactory {
  public static PosteDTO posteToPosteDTO(Poste poste) {
    PosteDTO posteDTO=new PosteDTO();
    posteDTO.setId(poste.getId());
    posteDTO.setDesignation(poste.getDesignation());
    posteDTO.setCode(poste.getCode());
    posteDTO.setUtilisateurList(poste.getUtilisateurList());
    return posteDTO;
  }

  public static Poste posteDTOToPoste(PosteDTO posteDTO) {
    Poste poste=new Poste();
    poste.setId(posteDTO.getId());
    poste.setDesignation(posteDTO.getDesignation());
    poste.setCode(posteDTO.getCode());
    poste.setUtilisateurList(posteDTO.getUtilisateurList());
    return poste;
  }

  public static Collection<PosteDTO> posteToPosteDTOs(Collection<Poste> postes) {
    List<PosteDTO> postesDTO=new ArrayList<>();
    postes.forEach(x -> {
      postesDTO.add(posteToPosteDTO(x));
    } );
    return postesDTO;
  }
}

