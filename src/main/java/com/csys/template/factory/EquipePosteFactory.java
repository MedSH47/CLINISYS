package com.csys.template.factory;

import com.csys.template.domain.EquipePoste;
import com.csys.template.dto.EquipePosteDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquipePosteFactory {
  public static EquipePosteDTO equipeposteToEquipePosteDTO(EquipePoste equipeposte) {
    EquipePosteDTO equipeposteDTO=new EquipePosteDTO();
    equipeposteDTO.setId(equipeposte.getId());
    equipeposteDTO.setIdEquipe(equipeposte.getIdEquipe());
    equipeposteDTO.setIdPoste(equipeposte.getIdPoste());
    return equipeposteDTO;
  }

  public static EquipePoste equipeposteDTOToEquipePoste(EquipePosteDTO equipeposteDTO) {
    EquipePoste equipeposte=new EquipePoste();
    equipeposte.setId(equipeposteDTO.getId());
    equipeposte.setIdEquipe(equipeposteDTO.getIdEquipe());
    equipeposte.setIdPoste(equipeposteDTO.getIdPoste());
    return equipeposte;
  }

  public static Collection<EquipePosteDTO> equipeposteToEquipePosteDTOs(Collection<EquipePoste> equipepostes) {
    List<EquipePosteDTO> equipepostesDTO=new ArrayList<>();
    equipepostes.forEach(x -> {
      equipepostesDTO.add(equipeposteToEquipePosteDTO(x));
    } );
    return equipepostesDTO;
  }
}

