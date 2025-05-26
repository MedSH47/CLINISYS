package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.dto.EquipeDTO;
import com.csys.template.dto.UtilisateurDTO;

// Assuming ModuleFactory.moduleToModuleDTOs exists and works as intended
// import com.csys.template.factory.ModuleFactory; 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquipeFactory {
  public static EquipeDTO equipeToEquipeDTO(Equipe equipe, boolean fetchDetails) {
    EquipeDTO equipeDTO = new EquipeDTO();
    if (equipe == null) {
        return null;
    }
    equipeDTO.setId(equipe.getId());
    equipeDTO.setDateCreation(equipe.getDateCreation());
    equipeDTO.setUserCreation(equipe.getUserCreation());
    equipeDTO.setDesignation(equipe.getDesignation());
    
    if (equipe.getChefEquipe() != null) {
        // Fetch basic info for chefEquipe, not their full details recursively
        equipeDTO.setChefEquipe(UtilisateurFactory.utilisateurToUtilisateurDTO( equipe.getChefEquipe(),false));
    }

    if (equipe.getModuleSet() != null) {
        // Assuming ModuleFactory.moduleToModuleDTOs handles its own lazy loading or provides basic DTOs
        equipeDTO.setModuleSet(ModuleFactory.moduleToModuleDTOs(equipe.getModuleSet()));
    } else {
        equipeDTO.setModuleSet(new ArrayList<>());
    }

    if (fetchDetails) {
        if (equipe.getEquipePosteutilisateurSet() != null) {
            equipeDTO.setEquipePosteutilisateurSet(
                EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTOs(
                    equipe.getEquipePosteutilisateurSet(), true // true: include User and Poste details in EPU
                )
            );
        } else {
            equipeDTO.setEquipePosteutilisateurSet(new ArrayList<>());
        }
    } else {
      // If not fetching details, set an empty list or null
      equipeDTO.setEquipePosteutilisateurSet(new ArrayList<>());
    }
    return equipeDTO;
  }

  // Overload for backward compatibility or default non-detailed conversion
  public static EquipeDTO equipeToEquipeDTO(Equipe equipe) {
    return equipeToEquipeDTO(equipe, false); // Default to not fetching details
  }

  public static Equipe equipeDTOToEquipe(EquipeDTO equipeDTO) {
    Equipe equipe = new Equipe();
    if (equipeDTO == null) {
        return null;
    }
    equipe.setId(equipeDTO.getId());
    equipe.setDateCreation(equipeDTO.getDateCreation());
    equipe.setUserCreation(equipeDTO.getUserCreation());
    equipe.setDesignation(equipeDTO.getDesignation());
    
    if (equipeDTO.getChefEquipe() != null) {
        equipe.setChefEquipe(UtilisateurFactory.utilisateurDTOToUtilisateur( equipeDTO.getChefEquipe()));
    }
    
    if (equipeDTO.getModuleSet() != null) {
        equipe.setModuleSet(ModuleFactory.moduleDTOToModules(equipeDTO.getModuleSet()));
    }
    // Mapping EquipePosteutilisateurSet back to entities would be complex here
    // equipe.setEquipePosteutilisateurSet( ... );
    return equipe;
  }

  public static Collection<EquipeDTO> equipeToEquipeDTOs(Collection<Equipe> equipes, boolean fetchDetails) {
    if (equipes == null) {
        return new ArrayList<>();
    }
    List<EquipeDTO> equipesDTO = new ArrayList<>();
    equipes.forEach(x -> {
      equipesDTO.add(equipeToEquipeDTO(x, fetchDetails));
    });
    return equipesDTO;
  }
  
  // Overload for backward compatibility or default behavior
  public static Collection<EquipeDTO> equipeToEquipeDTOs(Collection<Equipe> equipes) {
    return equipeToEquipeDTOs(equipes, false); // Default to not fetching details
  }
}