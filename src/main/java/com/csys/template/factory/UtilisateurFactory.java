package com.csys.template.factory;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UtilisateurFactory {
  public static UtilisateurDTO utilisateurToUtilisateurDTO(Utilisateur utilisateur) {
    UtilisateurDTO utilisateurDTO=new UtilisateurDTO();
    utilisateurDTO.setId(utilisateur.getId());
    utilisateurDTO.setNom(utilisateur.getNom());
    utilisateurDTO.setPrenom(utilisateur.getPrenom());
    utilisateurDTO.setNumTelephone(utilisateur.getNumTelephone());
    utilisateurDTO.setEmail(utilisateur.getEmail());
    utilisateurDTO.setUserCreation(utilisateur.getUserCreation());
    utilisateurDTO.setDateCreation(utilisateur.getDateCreation());
    utilisateurDTO.setMotDePasse(utilisateur.getMotDePasse());
    utilisateurDTO.setRole(utilisateur.getRole());
    utilisateurDTO.setActivite(utilisateur.getActivite());
    utilisateurDTO.setIdPoste(utilisateur.getIdPoste());
    return utilisateurDTO;
  }

  public static Utilisateur utilisateurDTOToUtilisateur(UtilisateurDTO utilisateurDTO) {
    Utilisateur utilisateur=new Utilisateur();
    utilisateur.setId(utilisateurDTO.getId());
    utilisateur.setNom(utilisateurDTO.getNom());
    utilisateur.setPrenom(utilisateurDTO.getPrenom());
    utilisateur.setNumTelephone(utilisateurDTO.getNumTelephone());
    utilisateur.setEmail(utilisateurDTO.getEmail());
    utilisateur.setUserCreation(utilisateurDTO.getUserCreation());
    utilisateur.setDateCreation(utilisateurDTO.getDateCreation());
    utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
    utilisateur.setRole(utilisateurDTO.getRole());
    utilisateur.setActivite(utilisateurDTO.getActivite());
    utilisateur.setIdPoste(utilisateurDTO.getIdPoste());
    return utilisateur;
  }

  public static Collection<UtilisateurDTO> utilisateurToUtilisateurDTOs(Collection<Utilisateur> utilisateurs) {
    List<UtilisateurDTO> utilisateursDTO=new ArrayList<>();
    utilisateurs.forEach(x -> {
      utilisateursDTO.add(utilisateurToUtilisateurDTO(x));
    } );
    return utilisateursDTO;
  }
}

