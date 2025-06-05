package com.csys.template.factory;

import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.dto.EquipePosteutilisateurDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EquipePosteutilisateurFactory {

    public static EquipePosteutilisateurDTO toDTO(EquipePosteutilisateur entity) {
        if (entity == null) return null;
        EquipePosteutilisateurDTO dto = new EquipePosteutilisateurDTO();
        dto.setEquipePosteutilisateurPK(entity.getEquipePosteutilisateurPK());
        dto.setEquipe(EquipeFactory.toDTOLight(entity.getEquipe()));
        dto.setPoste(PosteFactory.toDTOLight(entity.getPoste()));
        dto.setUtilisateur(UtilisateurFactory.toDTOLight(entity.getUtilisateur()));
        return dto;
    }

    public static EquipePosteutilisateur toEntity(EquipePosteutilisateurDTO dto) {
        if (dto == null) return null;
        EquipePosteutilisateur entity = new EquipePosteutilisateur();
        if (dto.getIdEquipe() != null && dto.getIdPoste() != null && dto.getIdUtilisateur() != null) {
            entity.setEquipePosteutilisateurPK(new EquipePosteutilisateurPK(
                dto.getIdPoste(), dto.getIdUtilisateur(), dto.getIdEquipe()));
        }
        entity.setEquipe(EquipeFactory.toEntity(dto.getEquipe()));
        entity.setPoste(PosteFactory.toEntity(dto.getPoste(),null,""));
        entity.setUtilisateur(UtilisateurFactory.toEntity(dto.getUtilisateur()));
        return entity;
    }

    public static List<EquipePosteutilisateurDTO> toDTOs(Collection<EquipePosteutilisateur> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(EquipePosteutilisateurFactory::toDTO).collect(Collectors.toList());
    }

    public static List<EquipePosteutilisateur> toEntities(Collection<EquipePosteutilisateurDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(EquipePosteutilisateurFactory::toEntity).collect(Collectors.toList());
    }
}