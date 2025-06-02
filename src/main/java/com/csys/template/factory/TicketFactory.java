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

        // FIX 1: Correctly handle the parent ticket.
        // The TicketDTO does not have a method setIdTicketParent(Integer).
        // It has setParentTicket(Ticket), so we will use that.
        // NOTE: Passing the direct entity can be risky for recursion, but it matches your DTO's design.
        if (ticket.getParentTicket() != null) {
            // Your DTO has a field `idTicketParent` but no public setter for it was provided.
            // We will use the `setParentTicket` method that is available.
             dto.setParentTicket(ticket.getParentTicket());
        }

        // FIX 2: Correctly set the Client.
        // The method `setIdClient` in your TicketDTO expects a `Client` entity, not a `ClientDTO`.
        // We now pass the entity directly from the ticket.
        dto.setIdClient(ticket.getIdClient());


        // These mappings were correct and use light DTOs to prevent cycles from other relationships.
        dto.setIdUtilisateur(UtilisateurFactory.toDTOLight(ticket.getIdUtilisateur()));
        dto.setIdModule(ModuleFactory.toDTOLight(ticket.getIdModule()));
        
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
        entity.setDescription(dto.getDescription());
        entity.setDateCreation(dto.getDateCreation());
        entity.setUserCreation(dto.getUserCreation());
        entity.setPriorite(dto.getPriorite());
        entity.setStatue(dto.getStatue());
        
        // The parent ticket relationship should be managed carefully in the service layer
        // to attach the correct managed entity.
        if (dto.getParentTicket() != null) {
            entity.setParentTicket(dto.getParentTicket());
        }

        // FIX 3: Correctly set the Client entity from the DTO.
        // The method `dto.getIdClient()` returns a `Client` entity directly.
        // We should not try to convert it again with `ClientFactory.toEntity`.
        entity.setIdClient(dto.getIdClient());


        // These mappings were correct.
        entity.setIdUtilisateur(UtilisateurFactory.toEntity(dto.getIdUtilisateur()));
        entity.setIdModule(ModuleFactory.toEntity(dto.getIdModule()));
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