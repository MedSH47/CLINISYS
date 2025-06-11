package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoRequest.EquipeRequestDTO;
import com.csys.template.dtoResponse.EquipeResponseDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.util.Helper;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EquipeFactory {

    public static EquipeResponseDTO toResponseDTO(Equipe equipe) {
        if (equipe == null) return null;
        EquipeResponseDTO dto = new EquipeResponseDTO();
        dto.setId(equipe.getId());
        dto.setDesignation(equipe.getDesignation());
        dto.setDateCreation(equipe.getDateCreation());
        dto.setUserCreation(equipe.getUserCreation());
        dto.setChefEquipe(UtilisateurFactory.toDTOLight(equipe.getChefEquipe()));
        dto.setModuleList(ModuleFactory.toDTOsLight(equipe.getModuleList()));

        if (equipe.getEquipePosteutilisateurList() != null) {
            List<UtilisateurResponseDTO> utilisateurs = equipe.getEquipePosteutilisateurList().stream()
                    .map(epu -> UtilisateurFactory.toDTOLight(epu.getUtilisateur()))
                    .distinct()
                    .collect(Collectors.toList());
            dto.setUtilisateurs(utilisateurs);
        }
        return dto;
    }
    
    public static EquipeResponseDTO toDTOLight(Equipe equipe) {
        if (equipe == null) return null;
        EquipeResponseDTO dto = new EquipeResponseDTO();
        dto.setId(equipe.getId());
        dto.setDesignation(equipe.getDesignation());
        return dto;
    }

    public static Equipe toEntity(EquipeRequestDTO dto) {
        if (dto == null) return null;
        Equipe entity = new Equipe();
        entity.setDesignation(dto.getDesignation());
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated());

        if (dto.getIdChefEquipe() != null) {
            Utilisateur chefEquipe = new Utilisateur();
            chefEquipe.setId(dto.getIdChefEquipe());
            entity.setChefEquipe(chefEquipe);
        }
        return entity;
    }

    public static List<EquipeResponseDTO> toResponseDTOs(Collection<Equipe> equipes) {
        if (equipes == null) return Collections.emptyList();
        return equipes.stream().map(EquipeFactory::toResponseDTO).collect(Collectors.toList());
    }
}