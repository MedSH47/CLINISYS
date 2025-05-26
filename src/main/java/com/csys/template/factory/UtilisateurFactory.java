package com.csys.template.factory;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.EquipeDTO; // Assuming EquipeDTO might be needed if EquipeSet is mapped
import com.csys.template.dto.TicketDTO; // Assuming TicketDTO might be needed if TicketSet is mapped
import com.csys.template.dto.UtilisateurDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors; // For mapping collections if needed

public class UtilisateurFactory {
  public static UtilisateurDTO utilisateurToUtilisateurDTO(Utilisateur utilisateur, boolean lazy) {
    UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
    if (utilisateur == null) {
        return null;
    }

    // Basic information always set
    utilisateurDTO.setId(utilisateur.getId());
    utilisateurDTO.setNom(utilisateur.getNom());
    utilisateurDTO.setPrenom(utilisateur.getPrenom());
    utilisateurDTO.setNumTelephone(utilisateur.getNumTelephone());
    utilisateurDTO.setEmail(utilisateur.getEmail());
    utilisateurDTO.setUserCreation(utilisateur.getUserCreation());
    utilisateurDTO.setDateCreation(utilisateur.getDateCreation());
    utilisateurDTO.setRole(utilisateur.getRole());
    utilisateurDTO.setActivite(utilisateur.getActivite());

    // Fields dependent on the lazy flag
    if (lazy) {
        utilisateurDTO.setMotDePasse(utilisateur.getMotDePasse());
        utilisateurDTO.setPhoto(utilisateur.getPhoto());
        if (utilisateur.getEquipePosteutilisateurSet() != null) {
            utilisateurDTO.setEquipePosteutilisateurSet(
                EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTOs(
                    utilisateur.getEquipePosteutilisateurSet(), true // true: include Equipe and Poste details in EPU
                )
            );
        } else {
            utilisateurDTO.setEquipePosteutilisateurSet(new ArrayList<>());
        }
    }

    // For TicketSet and EquipeSet (teams led by user), map to DTOs if needed
    // For now, sticking to the prompt's focus on EquipePosteutilisateurSet.
    // If these need to be DTOs, they would require their own factories and mapping logic.
    // Example (if TicketFactory.ticketToTicketDTOs exists and takes a lazy flag):
    // utilisateurDTO.setTicketSet(TicketFactory.ticketToTicketDTOs(utilisateur.getTicketSet(), false));
    // For current implementation based on provided files:
    utilisateurDTO.setTicketSet(utilisateur.getTicketSet()); // This might be Set<Ticket> entities

    // Example (if equipeToEquipeDTOs can handle null and takes a lazy flag):
    // if (utilisateur.getEquipeSet() != null) {
    //     utilisateurDTO.setEquipeSet(EquipeFactory.equipeToEquipeDTOs(utilisateur.getEquipeSet(), false));
    // } else {
    //     utilisateurDTO.setEquipeSet(new ArrayList<>());
    // }
    // For current implementation:
    utilisateurDTO.setEquipeSet(utilisateur.getEquipeSet()); // This might be Set<Equipe> entities

    return utilisateurDTO;
  }

  public static Utilisateur utilisateurDTOToUtilisateur(UtilisateurDTO utilisateurDTO) {
    Utilisateur utilisateur = new Utilisateur();
    if (utilisateurDTO == null) {
        return null;
    }
    utilisateur.setId(utilisateurDTO.getId());
    utilisateur.setNom(utilisateurDTO.getNom());
    utilisateur.setPrenom(utilisateurDTO.getPrenom());
    utilisateur.setNumTelephone(utilisateurDTO.getNumTelephone());
    utilisateur.setEmail(utilisateurDTO.getEmail());
    utilisateur.setUserCreation(utilisateurDTO.getUserCreation());
    utilisateur.setDateCreation(utilisateurDTO.getDateCreation());
    utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
    utilisateur.setPhoto(utilisateurDTO.getPhoto());
    utilisateur.setRole(utilisateurDTO.getRole());
    utilisateur.setActivite(utilisateurDTO.getActivite());
    utilisateur.setTicketSet(utilisateurDTO.getTicketSet()); // Assuming DTO holds entities or compatible type
    // Add mapping for equipePosteutilisateurSet if needed for entity conversion
    return utilisateur;
  }

  public static Collection<UtilisateurDTO> utilisateurToUtilisateurDTOs(Collection<Utilisateur> utilisateurs, boolean lazy) {
    if (utilisateurs == null) {
        return new ArrayList<>();
    }
    List<UtilisateurDTO> utilisateursDTO = new ArrayList<>();
    utilisateurs.forEach(x -> {
      utilisateursDTO.add(utilisateurToUtilisateurDTO(x, lazy));
    });
    return utilisateursDTO;
  }
   // Overload for backward compatibility or default behavior (e.g., no lazy loading)
  public static Collection<UtilisateurDTO> utilisateurToUtilisateurDTOs(Collection<Utilisateur> utilisateurs) {
    return utilisateurToUtilisateurDTOs(utilisateurs, false); // Default to not lazy loading details
  }
}