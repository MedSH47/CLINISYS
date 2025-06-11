package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.domain.Module;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.util.Helper;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TicketFactory {

    public static TicketResponseDTO toResponseDTO(Ticket ticket) {
        if (ticket == null) return null;
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTitre(ticket.getTitre());
        dto.setDescription(ticket.getDescription());
        dto.setDateCreation(ticket.getDateCreation());
        dto.setUserCreation(ticket.getUserCreation());
        dto.setPriorite(ticket.getPriorite());
        dto.setStatue(ticket.getStatue());
        
        // Use light DTOs for all nested objects
        dto.setIdUtilisateur(UtilisateurFactory.toDTOLight(ticket.getIdUtilisateur()));
        dto.setCommentaireList(CommentaireFactory.toResponseDTOs(ticket.getCommentaireList()));
        dto.setIdClient(ClientFactory.toDTOLight(ticket.getIdClient()));
        dto.setIdModule(ModuleFactory.toDTOLight(ticket.getModule()));
        
        if (ticket.getParentTicket() != null) {
             dto.setParentTicket(TicketFactory.toDTOLight(ticket.getParentTicket()));
        }
        return dto;
    }

    public static TicketResponseDTO toDTOLight(Ticket ticket) {
        if (ticket == null) return null;
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTitre(ticket.getTitre());
        dto.setStatue(ticket.getStatue());
        dto.setPriorite(ticket.getPriorite());
        dto.setDateCreation(ticket.getDateCreation());
        return dto;
    }

    public static Ticket toEntity(TicketRequestDTO dto) {
        if (dto == null) return null;
        Ticket entity = new Ticket();
        entity.setTitre(dto.getTitre());
        entity.setDescription(dto.getDescription());
        entity.setPriorite(dto.getPriorite());
        entity.setStatue(dto.getStatue());
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated());

        if (dto.getIdParentTicket() != null) {
            Ticket parent = new Ticket();
            parent.setId(dto.getIdParentTicket());
            entity.setParentTicket(parent);
        }
        if (dto.getIdClient() != null) {
            Client client = new Client();
            client.setId(dto.getIdClient());
            entity.setIdClient(client);
        }
        if (dto.getIdModule() != null) {
            Module module = new Module();
            module.setId(dto.getIdModule());
            entity.setModule(module);
        }
        if (dto.getIdUtilisateur() != null) {
            Utilisateur user = new Utilisateur();
            user.setId(dto.getIdUtilisateur());
            entity.setIdUtilisateur(user);
        }
        return entity;
    }

    public static List<TicketResponseDTO> toResponseDTOs(Collection<Ticket> tickets) {
        if (tickets == null) return Collections.emptyList();
        return tickets.stream().map(TicketFactory::toResponseDTO).collect(Collectors.toList());
    }

    public static List<TicketResponseDTO> toDTOsLight(Collection<Ticket> tickets) {
        if (tickets == null) return Collections.emptyList();
        return tickets.stream().map(TicketFactory::toDTOLight).collect(Collectors.toList());
    }
}