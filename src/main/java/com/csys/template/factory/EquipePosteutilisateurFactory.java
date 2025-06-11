package com.csys.template.factory;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.domain.Poste;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoRequest.EquipePosteutilisateurRequestDTO;
import com.csys.template.dtoResponse.EquipePosteutilisateurResponseDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EquipePosteutilisateurFactory {

    public static EquipePosteutilisateurResponseDTO toResponseDTO(EquipePosteutilisateur entity) {
        if (entity == null) return null;
        EquipePosteutilisateurResponseDTO dto = new EquipePosteutilisateurResponseDTO();
        dto.setEquipe(EquipeFactory.toDTOLight(entity.getEquipe()));
        dto.setPoste(PosteFactory.toDTOLight(entity.getPoste()));
        dto.setUtilisateur(UtilisateurFactory.toDTOLight(entity.getUtilisateur()));
        return dto;
    }

    public static EquipePosteutilisateur toEntity(EquipePosteutilisateurRequestDTO dto) {
        if (dto == null) return null;
        EquipePosteutilisateur entity = new EquipePosteutilisateur();
        
        if (dto.getIdEquipe() != null && dto.getIdPoste() != null && dto.getIdUtilisateur() != null) {
            entity.setEquipePosteutilisateurPK(new EquipePosteutilisateurPK(
                dto.getIdPoste(), dto.getIdUtilisateur(), dto.getIdEquipe()));
            
            Equipe equipe = new Equipe();
            equipe.setId(dto.getIdEquipe());
            entity.setEquipe(equipe);

            Poste poste = new Poste();
            poste.setId(dto.getIdPoste());
            entity.setPoste(poste);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(dto.getIdUtilisateur());
            entity.setUtilisateur(utilisateur);
        }
        return entity;
    }

    public static List<EquipePosteutilisateurResponseDTO> toResponseDTOs(Collection<EquipePosteutilisateur> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(EquipePosteutilisateurFactory::toResponseDTO).collect(Collectors.toList());
    }
}