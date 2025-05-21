package com.csys.template.factory;

import com.csys.template.domain.Avancement;
import com.csys.template.domain.Commentaire;
import com.csys.template.dto.AvancementDTO;
import com.csys.template.dto.CommentaireDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AvancementFactory {
  public static AvancementDTO avancementToAvancementDTO(Avancement avancement) {
    AvancementDTO avancementDTO=new AvancementDTO();
    avancementDTO.setId(avancement.getId());
    avancementDTO.setDateEcheance(avancement.getDateEcheance());
    avancementDTO.setDateDebut(avancement.getDateDebut());
    avancementDTO.setDateFin(avancement.getDateFin());
    avancementDTO.setDureeTravail(avancement.getDureeTravail());
    avancementDTO.setIdTicket(avancement.getIdTicket());
    Collection<CommentaireDTO> commentaireCollectionDtos = new ArrayList<>();
    avancement.getCommentaireCollection().forEach(x -> {
      CommentaireDTO commentaireDto = new CommentaireDTO();
      commentaireDto = CommentaireFactory.commentaireToCommentaireDTO(x);
      commentaireCollectionDtos.add(commentaireDto);
    } );
    if(avancementDTO.getCommentaireCollection() !=null) {
      avancementDTO.getCommentaireCollection().clear();
      avancementDTO.getCommentaireCollection().addAll(commentaireCollectionDtos);
    }
    else {
      avancementDTO.setCommentaireCollection(commentaireCollectionDtos);
    }
    return avancementDTO;
  }

  public static Avancement avancementDTOToAvancement(AvancementDTO avancementDTO) {
    Avancement avancement=new Avancement();
    avancement.setId(avancementDTO.getId());
    avancement.setDateEcheance(avancementDTO.getDateEcheance());
    avancement.setDateDebut(avancementDTO.getDateDebut());
    avancement.setDateFin(avancementDTO.getDateFin());
    avancement.setDureeTravail(avancementDTO.getDureeTravail());
    avancement.setIdTicket(avancementDTO.getIdTicket());
    Collection<Commentaire> commentaireCollections = new ArrayList<>();
    avancementDTO.getCommentaireCollection().forEach(x -> {
      Commentaire commentaire = new Commentaire();
      commentaire = CommentaireFactory.commentaireDTOToCommentaire(x);
      commentaireCollections.add(commentaire);
    } );
    if(avancement.getCommentaireCollection() !=null) {
      avancement.getCommentaireCollection().clear();
      avancement.getCommentaireCollection().addAll(commentaireCollections);
    }
    else {
      avancement.setCommentaireCollection(commentaireCollections);
    }
    return avancement;
  }

  public static Collection<AvancementDTO> avancementToAvancementDTOs(Collection<Avancement> avancements) {
    List<AvancementDTO> avancementsDTO=new ArrayList<>();
    avancements.forEach(x -> {
      avancementsDTO.add(avancementToAvancementDTO(x));
    } );
    return avancementsDTO;
  }

  public static AvancementDTO lazyavancementToAvancementDTO(Avancement avancement) {
    AvancementDTO avancementDTO=new AvancementDTO();
    avancementDTO.setId(avancement.getId());
    avancementDTO.setDateEcheance(avancement.getDateEcheance());
    avancementDTO.setDateDebut(avancement.getDateDebut());
    avancementDTO.setDateFin(avancement.getDateFin());
    avancementDTO.setDureeTravail(avancement.getDureeTravail());
    avancementDTO.setIdTicket(avancement.getIdTicket());
    return avancementDTO;
  }

  public static Collection<AvancementDTO> lazyavancementToAvancementDTOs(Collection<Avancement> avancements) {
    List<AvancementDTO> avancementsDTO=new ArrayList<>();
    avancements.forEach(x -> {
      avancementsDTO.add(lazyavancementToAvancementDTO(x));
    } );
    return avancementsDTO;
  }
}

