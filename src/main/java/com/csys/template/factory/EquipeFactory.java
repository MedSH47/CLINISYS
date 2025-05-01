package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.dto.EquipeDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquipeFactory {
  public static EquipeDTO equipeToEquipeDTO(Equipe equipe) {
    EquipeDTO equipeDTO=new EquipeDTO();
    equipeDTO.setId(equipe.getId());
    equipeDTO.setNomEquipe(equipe.getNomEquipe());
    equipeDTO.setCreationDate(equipe.getCreationDate());
    equipeDTO.setCreationUser(equipe.getCreationUser());
    equipeDTO.setTicketList(equipe.getTicketList());
    equipeDTO.setUtilisateurList(equipe.getUtilisateurList());
    return equipeDTO;
  }

  @SuppressWarnings("unchecked")
  public static Equipe equipeDTOToEquipe(EquipeDTO equipeDTO) {
    Equipe equipe=new Equipe();
    equipe.setId(equipeDTO.getId());
    equipe.setNomEquipe(equipeDTO.getNomEquipe());
    equipe.setCreationDate(equipeDTO.getCreationDate());
    equipe.setCreationUser(equipeDTO.getCreationUser());
    equipe.setTicketList(equipeDTO.getTicketList());
    equipe.setUtilisateurList(equipeDTO.getUtilisateurList());
    return equipe;
  }

  public static Collection<EquipeDTO> equipeToEquipeDTOs(Collection<Equipe> equipes) {
    List<EquipeDTO> equipesDTO=new ArrayList<>();
    equipes.forEach(x -> {
      equipesDTO.add(equipeToEquipeDTO(x));
    } );
    return equipesDTO;
  }
}

