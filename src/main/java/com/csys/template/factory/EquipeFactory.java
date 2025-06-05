package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.dto.EquipeDTO;
import com.csys.template.dto.EquipePosteutilisateurDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EquipeFactory {

    public static EquipeDTO toDTO(Equipe equipe) {
        if (equipe == null) return null;
        EquipeDTO dto = new EquipeDTO();
        dto.setId(equipe.getId());
        dto.setDesignation(equipe.getDesignation());
        dto.setDateCreation(equipe.getDateCreation());
        dto.setUserCreation(equipe.getUserCreation());
        dto.setModuleList(ModuleFactory.toDTOsLight(equipe.getModuleList()));
        dto.setChefEquipe(UtilisateurFactory.toDTOLight(equipe.getChefEquipe()));
        List<EquipePosteutilisateurDTO> equipePosteutilisateurDTOs =EquipePosteutilisateurFactory.toDTOs(equipe.getEquipePosteutilisateurList());
        // Remove duplicates based on utilisateur ID
        Set<Integer> seenIds = new HashSet<>();
        dto.setUtilisateurs(
            equipePosteutilisateurDTOs.stream()
                .map(EquipePosteutilisateurDTO::getUtilisateur)
                .filter(u -> seenIds.add(u.getId())) // only adds if ID is not already seen
                .collect(Collectors.toList())
        );
        return dto;
    }

    public static EquipeDTO toDTOLight(Equipe equipe) {
        if (equipe == null) return null;
        EquipeDTO dto = new EquipeDTO();
        dto.setId(equipe.getId());
        dto.setDesignation(equipe.getDesignation());
        return dto;
    }

    public static Equipe toEntity(EquipeDTO dto) {
        if (dto == null) return null;
        Equipe entity = new Equipe();
        entity.setId(dto.getId());
        entity.setDesignation(dto.getDesignation());
        entity.setDateCreation(dto.getDateCreation());
        entity.setUserCreation(dto.getUserCreation());
         
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