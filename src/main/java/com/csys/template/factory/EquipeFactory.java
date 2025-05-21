package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.EquipePoste;
import com.csys.template.domain.Module;
import com.csys.template.dto.EquipeDTO;
import com.csys.template.dto.EquipePosteDTO;
import com.csys.template.dto.ModuleDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquipeFactory {
  public static EquipeDTO equipeToEquipeDTO(Equipe equipe) {
    EquipeDTO equipeDTO=new EquipeDTO();
    equipeDTO.setId(equipe.getId());
    equipeDTO.setDesignation(equipe.getDesignation());
    Set<EquipePosteDTO> equipePosteCollectionDtos = new HashSet<>();
    equipe.getEquipePosteCollection().forEach(x -> {
      EquipePosteDTO equipeposteDto = new EquipePosteDTO();
      equipeposteDto = EquipePosteFactory.equipeposteToEquipePosteDTO(x);
      equipePosteCollectionDtos.add(equipeposteDto);
    } );
    if(equipeDTO.getEquipePosteCollection() !=null) {
      equipeDTO.getEquipePosteCollection().clear();
      equipeDTO.getEquipePosteCollection().addAll(equipePosteCollectionDtos);
    }
    else {
      equipeDTO.setEquipePosteCollection(equipePosteCollectionDtos);
    }
    Set<ModuleDTO> moduleCollectionDtos = new HashSet<>();
    equipe.getModuleCollection().forEach(x -> {
      ModuleDTO moduleDto = new ModuleDTO();
      moduleDto = ModuleFactory.moduleToModuleDTO(x);
      moduleCollectionDtos.add(moduleDto);
    } );
    if(equipeDTO.getModuleCollection() !=null) {
      equipeDTO.getModuleCollection().clear();
      equipeDTO.getModuleCollection().addAll(moduleCollectionDtos);
    }
    else {
      equipeDTO.setModuleCollection(moduleCollectionDtos);
    }
    return equipeDTO;
  }

  public static Equipe equipeDTOToEquipe(EquipeDTO equipeDTO) {
    Equipe equipe=new Equipe();
    equipe.setId(equipeDTO.getId());
    equipe.setDesignation(equipeDTO.getDesignation());
    Set<EquipePoste> equipePosteCollections = new HashSet<>();
    equipeDTO.getEquipePosteCollection().forEach(x -> {
      EquipePoste equipeposte = new EquipePoste();
      equipeposte = EquipePosteFactory.equipeposteDTOToEquipePoste(x);
      equipePosteCollections.add(equipeposte);
    } );
    if(equipe.getEquipePosteCollection() !=null) {
      equipe.getEquipePosteCollection().clear();
      equipe.getEquipePosteCollection().addAll(equipePosteCollections);
    }
    else {
      equipe.setEquipePosteCollection(equipePosteCollections);
    }
    Set<Module> moduleCollections = new HashSet<>();
    equipeDTO.getModuleCollection().forEach(x -> {
      Module module = new Module();
      module = ModuleFactory.moduleDTOToModule(x);
      moduleCollections.add(module);
    } );
    if(equipe.getModuleCollection() !=null) {
      equipe.getModuleCollection().clear();
      equipe.getModuleCollection().addAll(moduleCollections);
    }
    else {
      equipe.setModuleCollection(moduleCollections);
    }
    return equipe;
  }

  public static Collection<EquipeDTO> equipeToEquipeDTOs(Collection<Equipe> equipes) {
    List<EquipeDTO> equipesDTO=new ArrayList<>();
    equipes.forEach(x -> {
      equipesDTO.add(equipeToEquipeDTO(x));
    } );
    return equipesDTO;
  }

  public static EquipeDTO lazyequipeToEquipeDTO(Equipe equipe) {
    EquipeDTO equipeDTO=new EquipeDTO();
    equipeDTO.setId(equipe.getId());
    equipeDTO.setDesignation(equipe.getDesignation());
    return equipeDTO;
  }

  public static Collection<EquipeDTO> lazyequipeToEquipeDTOs(Collection<Equipe> equipes) {
    List<EquipeDTO> equipesDTO=new ArrayList<>();
    equipes.forEach(x -> {
      equipesDTO.add(lazyequipeToEquipeDTO(x));
    } );
    return equipesDTO;
  }
}

