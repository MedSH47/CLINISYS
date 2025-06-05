package com.csys.template.factory;

import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Role;
import com.csys.template.dto.EquipePosteDTO;
import com.csys.template.dto.UtilisateurDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

public class UtilisateurFactory {

    public static UtilisateurDTO toDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        if(utilisateur.getRole().equals(Role.Chef_Equipe)) return toDTOLight(utilisateur);
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setLogin(utilisateur.getLogin());
        dto.setEmail(utilisateur.getEmail());
        dto.setNumTelephone(utilisateur.getNumTelephone());
        dto.setMotDePasse(utilisateur.getMotDePasse());
        dto.setPhoto(utilisateur.getPhoto());
        dto.setRole(utilisateur.getRole());
        dto.setActivite(utilisateur.getActivite());
        dto.setDateCreation(utilisateur.getDateCreation());
        // Check if the ticketList is not null before mapping
        // This prevents NullPointerException if ticketList is null
        // If ticketList is null, we can set it to an empty list
        dto.setTicketList(utilisateur.getTicketList() != null ? utilisateur.getTicketList().stream()
            .map(TicketFactory::toDTOLight)
            .collect(Collectors.toList()) : Collections.emptyList());
        dto.setUserCreation(utilisateur.getUserCreation());
         if (utilisateur.getEquipePosteutilisateurList() != null) {
            Collection<EquipePosteDTO> equipePosteSet = utilisateur.getEquipePosteutilisateurList().stream()
                .map(epu -> {
                    EquipePosteDTO epuDTO = new EquipePosteDTO();
                    epuDTO.setEquipe(EquipeFactory.toDTOLight(epu.getEquipe()));
                    epuDTO.setPoste(PosteFactory.toDTOLight(epu.getPoste()));
                    epuDTO.setId(epu.getEquipePosteutilisateurPK().getIdEquipe()*10+epu.getEquipePosteutilisateurPK().getIdPoste()*100+epu.getEquipePosteutilisateurPK().getIdUtilisateur()*1000);
                    return epuDTO;
                })
                .collect(Collectors.toSet());

            dto.setEquipePosteSet(equipePosteSet);}
            return dto;
        }


    public static UtilisateurDTO toDTOLight(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setLogin(utilisateur.getLogin());
        dto.setEmail(utilisateur.getEmail());
        dto.setRole(utilisateur.getRole());
        return dto;
    }

    public static Utilisateur toEntity(UtilisateurDTO dto) {
        if (dto == null) return null;
        Utilisateur entity = new Utilisateur();
        entity.setId(dto.getId());
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setLogin(dto.getLogin());
        entity.setEmail(dto.getEmail());
        entity.setNumTelephone(dto.getNumTelephone());
        entity.setMotDePasse(dto.getMotDePasse());
        entity.setPhoto(dto.getPhoto());
        entity.setRole(dto.getRole());
        entity.setActivite(dto.getActivite());
        entity.setDateCreation(dto.getDateCreation());
        entity.setUserCreation(dto.getUserCreation());
        return entity;
    }

    public static List<UtilisateurDTO> toDTOs(Collection<Utilisateur> utilisateurs) {
        if (utilisateurs == null) return Collections.emptyList();
        return utilisateurs.stream().map(UtilisateurFactory::toDTO).collect(Collectors.toList());
    }
    
    public static List<UtilisateurDTO> toDTOsLight(Collection<Utilisateur> utilisateurs) {
        if (utilisateurs == null) return Collections.emptyList();
        return utilisateurs.stream().map(UtilisateurFactory::toDTOLight).collect(Collectors.toList());
    }

    public static List<Utilisateur> toEntities(Collection<UtilisateurDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(UtilisateurFactory::toEntity).collect(Collectors.toList());
    }
}