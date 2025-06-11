package com.csys.template.factory;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.EquipePosteDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.util.Helper;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UtilisateurFactory {

    public static UtilisateurResponseDTO toResponseDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;

        UtilisateurResponseDTO dto = new UtilisateurResponseDTO();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setLogin(utilisateur.getLogin());
        dto.setEmail(utilisateur.getEmail());
        dto.setNumTelephone(utilisateur.getNumTelephone());
        dto.setRole(utilisateur.getRole());
        dto.setActivite(utilisateur.getActivite());
        dto.setDateCreation(utilisateur.getDateCreation());
        dto.setUserCreation(utilisateur.getUserCreation());
        dto.setPhoto(utilisateur.getPhoto());
        dto.setTicketList(TicketFactory.toDTOsLight(utilisateur.getTicketList()));

        if (utilisateur.getEquipePosteutilisateurList() != null) {
            dto.setEquipePosteSet(utilisateur.getEquipePosteutilisateurList().stream()
                .map(epu -> {
                    EquipePosteDTO epuDTO = new EquipePosteDTO();
                    epuDTO.setEquipe(EquipeFactory.toDTOLight(epu.getEquipe()));
                    epuDTO.setPoste(PosteFactory.toDTOLight(epu.getPoste()));
                    return epuDTO;
                })
                .collect(Collectors.toSet()));
        }
        return dto;
    }

    public static UtilisateurResponseDTO toDTOLight(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        UtilisateurResponseDTO dto = new UtilisateurResponseDTO();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setLogin(utilisateur.getLogin());
        return dto;
    }

    public static Utilisateur toEntity(UtilisateurRequestDTO dto) {
        if (dto == null) return null;
        Utilisateur entity = new Utilisateur();
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setLogin(dto.getLogin());
        entity.setEmail(dto.getEmail());
        entity.setNumTelephone(dto.getNumTelephone());
        entity.setMotDePasse(dto.getMotDePasse());
        entity.setRole(dto.getRole());
        entity.setActivite(dto.getActivite());
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated()); 
        return entity;
    }

    public static List<UtilisateurResponseDTO> toResponseDTOs(Collection<Utilisateur> utilisateurs) {
        if (utilisateurs == null) return Collections.emptyList();
        return utilisateurs.stream().map(UtilisateurFactory::toResponseDTO).collect(Collectors.toList());
    }
}