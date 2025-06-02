package com.csys.template.factory;

import com.csys.template.domain.Ticketfichier;
import com.csys.template.dto.TicketfichierDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory for converting between the Ticketfichier join entity and its DTO.
 */
public class TicketfichierFactory {

    public static TicketfichierDTO toDTO(Ticketfichier ticketfichier) {
        if (ticketfichier == null) return null;

        TicketfichierDTO dto = new TicketfichierDTO();
        dto.setId(ticketfichier.getId());
        dto.setIdTicket(ticketfichier.getIdTicket());
        dto.setIdFichier(ticketfichier.getIdFichier());

        return dto;
    }
    
    public static TicketfichierDTO toDTOLight(Ticketfichier ticketfichier) {
        if (ticketfichier == null) return null;

        TicketfichierDTO dto = new TicketfichierDTO();
        dto.setId(ticketfichier.getId());
        // For a light version, we might only return the ID, or a subset of fields.
        // In this case, the full DTO is already quite light, so the methods are similar.
        return dto;
    }

    public static Ticketfichier toEntity(TicketfichierDTO dto) {
        if (dto == null) return null;

        Ticketfichier entity = new Ticketfichier();
        entity.setId(dto.getId());
        entity.setIdTicket(dto.getIdTicket());
        entity.setIdFichier(dto.getIdFichier());

        return entity;
    }

    public static List<TicketfichierDTO> toDTOs(Collection<Ticketfichier> ticketfichiers) {
        if (ticketfichiers == null) return Collections.emptyList();
        return ticketfichiers.stream().map(TicketfichierFactory::toDTO).collect(Collectors.toList());
    }

    public static List<Ticketfichier> toEntities(Collection<TicketfichierDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(TicketfichierFactory::toEntity).collect(Collectors.toList());
    }
}