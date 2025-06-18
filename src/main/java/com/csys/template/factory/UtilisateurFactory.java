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
        dto.setActif(utilisateur.getActif());
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
        entity.setActif(dto.getActif());
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated()); 
        entity.setPhoto(dto.getPhoto());
        return entity;
    }
    public static void updateFromDTO(Utilisateur entity, UtilisateurRequestDTO dto) {
    if (entity == null || dto == null) {
        return;
    }

    // Update scalar fields only if the DTO provided a non-null value
    if (dto.getNom() != null) {
        entity.setNom(dto.getNom());
    }
    if (dto.getPrenom() != null) {
        entity.setPrenom(dto.getPrenom());
    }
    if (dto.getLogin() != null) {
        entity.setLogin(dto.getLogin());
    }
    if (dto.getEmail() != null) {
        entity.setEmail(dto.getEmail());
    }
    if (dto.getNumTelephone() != null) {
        entity.setNumTelephone(dto.getNumTelephone());
    }
    if (dto.getMotDePasse() != null) {
        entity.setMotDePasse(dto.getMotDePasse());
    }
    if (dto.getPhoto() != null) {
        entity.setPhoto(dto.getPhoto());
    }

    // Enums and Booleans
    if (dto.getRole() != null) {
        entity.setRole(dto.getRole());
    }
    if (dto.getActif() != null) {
        entity.setActif(dto.getActif());
    }

    // NOTE: we generally do NOT update dateCreation/userCreation here,
    // since those are set on creation and should remain immutable.
}


    public static List<UtilisateurResponseDTO> toResponseDTOs(Collection<Utilisateur> utilisateurs) {
        if (utilisateurs == null) return Collections.emptyList();
        return utilisateurs.stream().map(UtilisateurFactory::toResponseDTO).collect(Collectors.toList());
    }
}