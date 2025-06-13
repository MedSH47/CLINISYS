package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.Module;
import com.csys.template.dtoRequest.ModuleRequestDTO;
import com.csys.template.dtoResponse.ModuleResponseDTO;
import com.csys.template.util.Helper;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleFactory {

    public static ModuleResponseDTO toResponseDTO(Module module) {
        if (module == null) return null;
        ModuleResponseDTO dto = new ModuleResponseDTO();
        dto.setId(module.getId());
        dto.setDesignation(module.getDesignation());
        dto.setDateCreation(module.getDateCreation());
        dto.setUserCreation(module.getUserCreation());
       dto.setEquipe(EquipeFactory.toDTOLight(module.getEquipe()));
        dto.setTicketList(TicketFactory.toDTOsLight(module.getTicketSet()));
        dto.setActif(module.getActif());
        return dto;
    }
    
    public static ModuleResponseDTO toDTOLight(Module module) {
        if (module == null) return null;
        ModuleResponseDTO dto = new ModuleResponseDTO();
        dto.setId(module.getId());
        dto.setEquipe(EquipeFactory.toDTOLight(module.getEquipe()));
        return dto;
    }

    public static Module toEntity(ModuleRequestDTO dto) {
        if (dto == null) return null;
        Module entity = new Module();
        entity.setDesignation(dto.getDesignation());
        
        if (dto.getIdEquipe() != null) {
            Equipe equipe = new Equipe();
            equipe.setId(dto.getIdEquipe());
            entity.setEquipe(equipe);
        }
        
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated());
        entity.setActif(dto.getActif() != null ? dto.getActif() : true);
        return entity;
    }

    public static List<ModuleResponseDTO> toResponseDTOs(Collection<Module> modules) {
        if (modules == null) return Collections.emptyList();
        return modules.stream().map(ModuleFactory::toResponseDTO).collect(Collectors.toList());
    }

    public static List<ModuleResponseDTO> toDTOsLight(Collection<Module> modules) {
        if (modules == null) return Collections.emptyList();
        return modules.stream().map(ModuleFactory::toDTOLight).collect(Collectors.toList());
    }
}