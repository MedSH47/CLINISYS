package com.csys.template.factory;

import com.csys.template.domain.Poste;
import com.csys.template.dto.PosteDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PosteFactory {
  public static PosteDTO posteToPosteDTO(Poste poste, boolean fetchDetails) {
    PosteDTO posteDTO = new PosteDTO();
    if (poste == null) {
        return null;
    }
    posteDTO.setId(poste.getId());
    posteDTO.setDateCreation(poste.getDateCreation());
    posteDTO.setUserCreation(poste.getUserCreation());
    posteDTO.setDesignation(poste.getDesignation());

    if (fetchDetails) {
        if (poste.getEquipePosteutilisateurSet() != null) {
            posteDTO.setEquipePosteutilisateurSet(
                EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTOs(
                    poste.getEquipePosteutilisateurSet(), true // true: include User and Equipe details in EPU
                )
            );
        } else {
            posteDTO.setEquipePosteutilisateurSet(new ArrayList<>());
        }
    } else {
      // If not fetching details, set an empty list or null
      posteDTO.setEquipePosteutilisateurSet(new ArrayList<>());
    }
    return posteDTO;
  }
  
  // Overload for backward compatibility or default non-detailed conversion
  public static PosteDTO posteToPosteDTO(Poste poste) {
    return posteToPosteDTO(poste, false); // Default to not fetching details
  }

  public static Poste posteDTOToPoste(PosteDTO posteDTO) {
    Poste poste = new Poste();
    if (posteDTO == null) {
        return null;
    }
    poste.setId(posteDTO.getId());
    poste.setDateCreation(posteDTO.getDateCreation());
    poste.setUserCreation(posteDTO.getUserCreation());
    poste.setDesignation(posteDTO.getDesignation());
    // If EquipePosteutilisateurSet needs to be mapped back to entities:
    // This would require EquipePosteutilisateurFactory.equipeposteutilisateurDTOToEquipePosteutilisateurs
    // and careful handling of nested entities.
    // poste.setEquipePosteutilisateurSet( ... ); 
    return poste;
  }

  public static Collection<PosteDTO> posteToPosteDTOs(Collection<Poste> postes, boolean fetchDetails) {
    if (postes == null) {
        return new ArrayList<>();
    }
    List<PosteDTO> postesDTO = new ArrayList<>();
    postes.forEach(x -> {
      postesDTO.add(posteToPosteDTO(x, fetchDetails));
    });
    return postesDTO;
  }

  // Overload for backward compatibility or default behavior
  public static Collection<PosteDTO> posteToPosteDTOs(Collection<Poste> postes) {
    return posteToPosteDTOs(postes, false); // Default to not fetching details
  }
}