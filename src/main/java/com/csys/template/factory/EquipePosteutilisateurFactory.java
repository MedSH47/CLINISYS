package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.Poste;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.EquipePosteutilisateurDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquipePosteutilisateurFactory {

  public static EquipePosteutilisateurDTO equipeposteutilisateurToEquipePosteutilisateurDTO(
      EquipePosteutilisateur equipeposteutilisateur, boolean includeReferencedDetails) {
    EquipePosteutilisateurDTO equipeposteutilisateurDTO = new EquipePosteutilisateurDTO();
    if (equipeposteutilisateur == null) {
        return null;
    }
    equipeposteutilisateurDTO.setEquipePosteutilisateurPK(equipeposteutilisateur.getEquipePosteutilisateurPK());

    if (includeReferencedDetails) {
      // Fetch Equipe basic details (e.g., ID, designation) but not its full EPU set again
      if (equipeposteutilisateur.getEquipe() != null) {
        equipeposteutilisateurDTO.setEquipe(EquipeFactory.equipeToEquipeDTO(equipeposteutilisateur.getEquipe(), false));
      }
      // Fetch Poste basic details (e.g., ID, designation) but not its full EPU set again
      if (equipeposteutilisateur.getPoste() != null) {
        equipeposteutilisateurDTO.setPoste(PosteFactory.posteToPosteDTO(equipeposteutilisateur.getPoste(), false));
      }
      // Fetch Utilisateur basic details (e.g., ID, name) but not their full EPU set again
      if (equipeposteutilisateur.getUtilisateur() != null) {
        equipeposteutilisateurDTO.setUtilisateur(UtilisateurFactory.utilisateurToUtilisateurDTO(equipeposteutilisateur.getUtilisateur(), false));
      }
    }
    return equipeposteutilisateurDTO;
  }

  public static EquipePosteutilisateur equipeposteutilisateurDTOToEquipePosteutilisateur(
      EquipePosteutilisateurDTO equipeposteutilisateurDTO,
      Equipe equipe, // These are passed in if known, or fetched if PK contains IDs
      Poste poste,
      Utilisateur utilisateur) {
    EquipePosteutilisateur equipeposteutilisateur = new EquipePosteutilisateur();
    if (equipeposteutilisateurDTO == null) {
        return null;
    }
    equipeposteutilisateur.setEquipePosteutilisateurPK(equipeposteutilisateurDTO.getEquipePosteutilisateurPK());
    
    // If equipe, poste, utilisateur are not provided, they might need to be fetched based on PK
    // or constructed if the DTO contains enough info.
    equipeposteutilisateur.setEquipe(equipe != null ? equipe : (equipeposteutilisateurDTO.getEquipe() != null ? EquipeFactory.equipeDTOToEquipe(equipeposteutilisateurDTO.getEquipe()) : null));
    equipeposteutilisateur.setPoste(poste != null ? poste : (equipeposteutilisateurDTO.getPoste() != null ? PosteFactory.posteDTOToPoste(equipeposteutilisateurDTO.getPoste()) : null));
    equipeposteutilisateur.setUtilisateur(utilisateur != null ? utilisateur : (equipeposteutilisateurDTO.getUtilisateur() != null ? UtilisateurFactory.utilisateurDTOToUtilisateur(equipeposteutilisateurDTO.getUtilisateur()) : null));
    
    return equipeposteutilisateur;
  }
  
  // Simpler version if Equipe, Poste, Utilisateur entities are expected to be resolved by the caller
   public static EquipePosteutilisateur equipeposteutilisateurDTOToEquipePosteutilisateur(
      EquipePosteutilisateurDTO equipeposteutilisateurDTO) {
    EquipePosteutilisateur equipeposteutilisateur = new EquipePosteutilisateur();
    if (equipeposteutilisateurDTO == null) {
        return null;
    }
    equipeposteutilisateur.setEquipePosteutilisateurPK(equipeposteutilisateurDTO.getEquipePosteutilisateurPK());
    
    // Expects that the service layer will handle setting the actual entity references
    if (equipeposteutilisateurDTO.getEquipe() != null) {
         Equipe equipe = new Equipe();
         equipe.setId(equipeposteutilisateurDTO.getEquipe().getId()); // Minimal reference
         equipeposteutilisateur.setEquipe(equipe);
    }
     if (equipeposteutilisateurDTO.getPoste() != null) {
         Poste poste = new Poste();
         poste.setId(equipeposteutilisateurDTO.getPoste().getId()); // Minimal reference
         equipeposteutilisateur.setPoste(poste);
    }
    if (equipeposteutilisateurDTO.getUtilisateur() != null) {
         Utilisateur utilisateur = new Utilisateur();
         utilisateur.setId(equipeposteutilisateurDTO.getUtilisateur().getId()); // Minimal reference
         equipeposteutilisateur.setUtilisateur(utilisateur);
    }
    return equipeposteutilisateur;
  }


  public static Collection<EquipePosteutilisateurDTO> equipeposteutilisateurToEquipePosteutilisateurDTOs(
      Collection<EquipePosteutilisateur> equipeposteutilisateurs, boolean includeReferencedDetails) {
    if (equipeposteutilisateurs == null) {
        return new ArrayList<>();
    }
    List<EquipePosteutilisateurDTO> equipeposteutilisateursDTO = new ArrayList<>();
    equipeposteutilisateurs.forEach(x -> {
      equipeposteutilisateursDTO.add(equipeposteutilisateurToEquipePosteutilisateurDTO(x, includeReferencedDetails));
    });
    return equipeposteutilisateursDTO;
  }
}