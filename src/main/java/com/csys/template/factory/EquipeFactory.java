package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.dto.EquipeDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EquipeFactory {

    public static EquipeDTO toDTO(Equipe equipe) {
        if (equipe == null) return null;
        EquipeDTO dto = new EquipeDTO();
        dto.setId(equipe.getId());
        dto.setDesignation(equipe.getDesignation());
        dto.setDateCreation(equipe.getDateCreation());
        dto.setUserCreation(equipe.getUserCreation());
        dto.setChefEquipe(UtilisateurFactory.toDTOLight(equipe.getChefEquipe()));
        if (equipe.getEquipePosteutilisateurSet() != null) {
             dto.setEquipePosteutilisateurSet(EquipePosteutilisateurFactory.toDTOs(equipe.getEquipePosteutilisateurSet()));
        }
        if (equipe.getModuleSet() != null) {
            dto.setModuleSet(ModuleFactory.toDTOsLight(equipe.getModuleSet()));
        }
        return dto;
    }

    public static EquipeDTO toDTOLight(Equipe equipe) {
        if (equipe == null) return null;
        EquipeDTO dto = new EquipeDTO();
        dto.setId(equipe.getId());
        dto.setDesignation(equipe.getDesignation());
        dto.setChefEquipe(UtilisateurFactory.toDTOLight(equipe.getChefEquipe()));
        return dto;
    }

    public static Equipe toEntity(EquipeDTO dto) {
        if (dto == null) return null;
        Equipe entity = new Equipe();
        entity.setId(dto.getId());
        entity.setDesignation(dto.getDesignation());
        entity.setDateCreation(dto.getDateCreation());
        entity.setUserCreation(dto.getUserCreation());
        entity.setChefEquipe(UtilisateurFactory.toEntity(dto.getChefEquipe()));
        return entity;
    }

    public static List<EquipeDTO> toDTOs(Collection<Equipe> equipes) {
        if (equipes == null) return Collections.emptyList();
        return equipes.stream().map(EquipeFactory::toDTO).collect(Collectors.toList());
    }

    public static List<EquipeDTO> toDTOsLight(Collection<Equipe> equipes) {
        if (equipes == null) return Collections.emptyList();
        return equipes.stream().map(EquipeFactory::toDTOLight).collect(Collectors.toList());
    }

    public static List<Equipe> toEntities(Collection<EquipeDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(EquipeFactory::toEntity).collect(Collectors.toList());
    }
}