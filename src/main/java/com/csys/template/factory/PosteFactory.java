package com.csys.template.factory;

import com.csys.template.domain.EquipePoste;
import com.csys.template.domain.Poste;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.EquipePosteDTO;
import com.csys.template.dto.PosteDTO;
import com.csys.template.dto.UtilisateurDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PosteFactory {
  public static PosteDTO posteToPosteDTO(Poste poste) {
    PosteDTO posteDTO=new PosteDTO();
    posteDTO.setId(poste.getId());
    posteDTO.setDesignation(poste.getDesignation());
    Set<EquipePosteDTO> equipePosteCollectionDtos = new HashSet<>();
    poste.getEquipePosteCollection().forEach(x -> {
      EquipePosteDTO equipeposteDto = new EquipePosteDTO();
      equipeposteDto = EquipePosteFactory.equipeposteToEquipePosteDTO(x);
      equipePosteCollectionDtos.add(equipeposteDto);
    } );
    if(posteDTO.getEquipePosteCollection() !=null) {
      posteDTO.getEquipePosteCollection().clear();
      posteDTO.getEquipePosteCollection().addAll(equipePosteCollectionDtos);
    }
    else {
      posteDTO.setEquipePosteCollection(equipePosteCollectionDtos);
    }
    Set<UtilisateurDTO> utilisateurCollectionDtos = new HashSet<>();
    poste.getUtilisateurCollection().forEach(x -> {
      UtilisateurDTO utilisateurDto = new UtilisateurDTO();
      utilisateurDto = UtilisateurFactory.utilisateurToUtilisateurDTO(x);
      utilisateurCollectionDtos.add(utilisateurDto);
    } );
    if(posteDTO.getUtilisateurCollection() !=null) {
      posteDTO.getUtilisateurCollection().clear();
      posteDTO.getUtilisateurCollection().addAll(utilisateurCollectionDtos);
    }
    else {
      posteDTO.setUtilisateurCollection(utilisateurCollectionDtos);
    }
    return posteDTO;
  }

  public static Poste posteDTOToPoste(PosteDTO posteDTO) {
    Poste poste=new Poste();
    poste.setId(posteDTO.getId());
    poste.setDesignation(posteDTO.getDesignation());
    Set<EquipePoste> equipePosteCollections = new HashSet<>();
    posteDTO.getEquipePosteCollection().forEach(x -> {
      EquipePoste equipeposte = new EquipePoste();
      equipeposte = EquipePosteFactory.equipeposteDTOToEquipePoste(x);
      equipePosteCollections.add(equipeposte);
    } );
    if(poste.getEquipePosteCollection() !=null) {
      poste.getEquipePosteCollection().clear();
      poste.getEquipePosteCollection().addAll(equipePosteCollections);
    }
    else {
      poste.setEquipePosteCollection(equipePosteCollections);
    }
    Set<Utilisateur> utilisateurCollections = new HashSet<>();
    posteDTO.getUtilisateurCollection().forEach(x -> {
      Utilisateur utilisateur = new Utilisateur();
      utilisateur = UtilisateurFactory.utilisateurDTOToUtilisateur(x);
      utilisateurCollections.add(utilisateur);
    } );
    if(poste.getUtilisateurCollection() !=null) {
      poste.getUtilisateurCollection().clear();
      poste.getUtilisateurCollection().addAll(utilisateurCollections);
    }
    else {
      poste.setUtilisateurCollection(utilisateurCollections);
    }
    return poste;
  }

  public static Collection<PosteDTO> posteToPosteDTOs(Collection<Poste> postes) {
    List<PosteDTO> postesDTO=new ArrayList<>();
    postes.forEach(x -> {
      postesDTO.add(posteToPosteDTO(x));
    } );
    return postesDTO;
  }



  

  public static PosteDTO lazyposteToPosteDTO(Poste poste) {
    PosteDTO posteDTO=new PosteDTO();
    posteDTO.setId(poste.getId());
    posteDTO.setDesignation(poste.getDesignation());
    return posteDTO;
  }

  public static Collection<PosteDTO> lazyposteToPosteDTOs(Collection<Poste> postes) {
    List<PosteDTO> postesDTO=new ArrayList<>();
    postes.forEach(x -> {
      postesDTO.add(lazyposteToPosteDTO(x));
    } );
    return postesDTO;
  }
}

