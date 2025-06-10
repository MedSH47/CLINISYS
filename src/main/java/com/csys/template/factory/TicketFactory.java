package com.csys.template.factory;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketDTO;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TicketFactory {

    public static TicketDTO toDTO(Ticket ticket) {
        if (ticket == null) return null;
        TicketDTO dto = new TicketDTO();
        if (dto.getParentTicket()!=null) return toDTOLight(ticket);
        dto.setId(ticket.getId());
        dto.setTitre(ticket.getTitre());
        dto.setDescription(ticket.getDescription());
        dto.setDateCreation(ticket.getDateCreation());
        dto.setUserCreation(ticket.getUserCreation());
        dto.setPriorite(ticket.getPriorite());
        dto.setStatue(ticket.getStatue());
        dto.setIdUtilisateur(UtilisateurFactory.toDTOLight(ticket.getIdUtilisateur()));
        dto.setCommentaireList(CommentaireFactory.toDTOsLight(ticket.getCommentaireList()));
        dto.setIdClient(ClientFactory.toDTOLight(ticket.getIdClient()));
        dto.setIdModule(ModuleFactory.toDTOLight(ticket.getModule()));
        if (ticket.getParentTicket() != null) {
             dto.setParentTicket(TicketFactory.toDTOLight(ticket.getParentTicket()));
        }
        return dto;
    }

    public static TicketDTO toDTOLight(Ticket ticket) {
        if (ticket == null) return null;
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTitre(ticket.getTitre());
        dto.setStatue(ticket.getStatue());
        return dto;
    }

    public static Ticket toEntity(TicketDTO dto) {
        if (dto == null) return null;
        Ticket entity = new Ticket();
        entity.setId(dto.getId());
        entity.setTitre(dto.getTitre());
        entity.setIdUtilisateur(UtilisateurFactory.toEntity(dto.getIdUtilisateur()));
        entity.setDescription(dto.getDescription());
        entity.setDateCreation(dto.getDateCreation());
        entity.setUserCreation(dto.getUserCreation());
        entity.setPriorite(dto.getPriorite());
        entity.setModule(ModuleFactory.toEntity(dto.getIdModule()));
        entity.setStatue(dto.getStatue());
        if (dto.getParentTicket() != null) {
            entity.setParentTicket(TicketFactory.toEntity(dto.getParentTicket()));
        }
        return entity;
    }

    public static List<TicketDTO> toDTOs(Collection<Ticket> tickets) {
        if (tickets == null) return Collections.emptyList();
        return tickets.stream().map(TicketFactory::toDTO).collect(Collectors.toList());
    }
    public static List<TicketDTO> toDTOs(Collection<Ticket> tickets,boolean lazy) {
        if (tickets == null) return Collections.emptyList();
        if (lazy) return tickets.stream().map(TicketFactory::toDTOLight).collect(Collectors.toList());
        return tickets.stream().map(TicketFactory::toDTO).collect(Collectors.toList());
    }

    public static List<TicketDTO> toDTOsLight(Collection<Ticket> tickets) {
        if (tickets == null) return Collections.emptyList();
        return tickets.stream().map(TicketFactory::toDTOLight).collect(Collectors.toList());
    }

    public static List<Ticket> toEntities(Collection<TicketDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(TicketFactory::toEntity).collect(Collectors.toList());
    }
}