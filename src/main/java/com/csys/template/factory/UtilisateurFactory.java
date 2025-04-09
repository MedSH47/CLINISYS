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
    utilisateurDTO.setLogin(utilisateur.getLogin());
    utilisateurDTO.setPassword(utilisateur.getPassword());
    utilisateurDTO.setCreationDate(utilisateur.getCreationDate());
    utilisateurDTO.setCreationUser(utilisateur.getCreationUser());
    utilisateurDTO.setActif(utilisateur.getActif());
    utilisateurDTO.setRole(utilisateur.getRole());
    utilisateurDTO.setIdEquip(utilisateur.getIdEquip());
    utilisateurDTO.setIdPoste(utilisateur.getIdPoste());
    return utilisateurDTO;
  }

  public static Utilisateur utilisateurDTOToUtilisateur(UtilisateurDTO utilisateurDTO) {
    Utilisateur utilisateur=new Utilisateur();
    utilisateur.setId(utilisateurDTO.getId());
    utilisateur.setLogin(utilisateurDTO.getLogin());
    utilisateur.setPassword(utilisateurDTO.getPassword());
    utilisateur.setCreationDate(utilisateurDTO.getCreationDate());
    utilisateur.setCreationUser(utilisateurDTO.getCreationUser());
    utilisateur.setActif(utilisateurDTO.getActif());
    utilisateur.setRole(utilisateurDTO.getRole());
    utilisateur.setIdEquip(utilisateurDTO.getIdEquip());
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

