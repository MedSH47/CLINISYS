package com.csys.template.factory;

import com.csys.template.domain.Module;
import com.csys.template.dto.ModuleDTO;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleFactory {

    public static ModuleDTO toDTO(Module module) {
        if (module == null) return null;
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setDesignation(module.getDesignation());
        dto.setDateCreation(module.getDateCreation());
        dto.setUserCreation(module.getUserCreation());
        dto.setEquipe(EquipeFactory.toDTO(module.getEquipe()));
        dto.setTicketSet(TicketFactory.toDTOsLight(module.getTicketSet()));
        return dto;
    }
    
    public static ModuleDTO toDTOLight(Module module) {
        if (module == null) return null;
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setDesignation(module.getDesignation());
        return dto;
    }

    public static Module toEntity(ModuleDTO dto) {
        if (dto == null) return null;
        Module entity = new Module();
        entity.setId(dto.getId());
        entity.setDesignation(dto.getDesignation());
        entity.setDateCreation(dto.getDateCreation());
        entity.setUserCreation(dto.getUserCreation());
        return entity;
    }

    public static List<ModuleDTO> toDTOs(Collection<Module> modules) {
        if (modules == null) return Collections.emptyList();
        return modules.stream().map(ModuleFactory::toDTO).collect(Collectors.toList());
    }

    public static List<ModuleDTO> toDTOsLight(Collection<Module> modules) {
        if (modules == null) return Collections.emptyList();
        return modules.stream().map(ModuleFactory::toDTOLight).collect(Collectors.toList());
    }

    public static List<Module> toEntities(Collection<ModuleDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(ModuleFactory::toEntity).collect(Collectors.toList());
    }
}