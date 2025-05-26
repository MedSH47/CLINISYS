package com.csys.template.factory;

import com.csys.template.domain.Avancement;
import com.csys.template.dto.AvancementDTO;
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
    return avancement;
  }

  public static Collection<AvancementDTO> avancementToAvancementDTOs(Collection<Avancement> avancements) {
    List<AvancementDTO> avancementsDTO=new ArrayList<>();
    avancements.forEach(x -> {
      avancementsDTO.add(avancementToAvancementDTO(x));
    } );
    return avancementsDTO;
  }
}

