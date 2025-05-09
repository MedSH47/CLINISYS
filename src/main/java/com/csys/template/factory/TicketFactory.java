package com.csys.template.factory;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TicketFactory {
  public static TicketDTO ticketToTicketDTO(Ticket ticket) {
    TicketDTO ticketDTO=new TicketDTO();
    ticketDTO.setId(ticket.getId());
    ticketDTO.setNumTicket(ticket.getNumTicket());
    ticketDTO.setDesignation(ticket.getDesignation());
    ticketDTO.setStatus(ticket.getStatus());
    ticketDTO.setPriorite(ticket.getPriorite());
    ticketDTO.setDateEffectationEquip(ticket.getDateEffectationEquip());
    ticketDTO.setDateCreation(ticket.getDateCreation());
    ticketDTO.setCreationUser(ticket.getCreationUser());
    ticketDTO.setCollaborateur(ticket.getCollaborateur());
    ticketDTO.setEcheance(ticket.getEcheance());
    ticketDTO.setIdClient(ticket.getIdClient());
    ticketDTO.setIdEquip(ticket.getIdEquip());
    ticketDTO.setIdModule(ticket.getIdModule());
    return ticketDTO;
  }

  public static Ticket ticketDTOToTicket(TicketDTO ticketDTO) {
    Ticket ticket=new Ticket();
    ticket.setId(ticketDTO.getId());
    ticket.setDesignation(ticketDTO.getDesignation());
    ticket.setNumTicket(ticketDTO.getNumTicket());
    ticket.setStatus(ticketDTO.getStatus());
    ticket.setPriorite(ticketDTO.getPriorite());
    ticket.setDateEffectationEquip(ticketDTO.getDateEffectationEquip());
    ticket.setDateCreation(ticketDTO.getDateCreation());
    ticket.setCreationUser(ticketDTO.getCreationUser());
    ticket.setCollaborateur(ticketDTO.getCollaborateur());
    ticket.setEcheance(ticketDTO.getEcheance());
    ticket.setIdClient(ticketDTO.getIdClient());
    ticket.setIdEquip(ticketDTO.getIdEquip());
    ticket.setIdModule(ticketDTO.getIdModule());
    return ticket;
  }

  public static Collection<TicketDTO> ticketToTicketDTOs(Collection<Ticket> tickets) {
    List<TicketDTO> ticketsDTO=new ArrayList<>();
    tickets.forEach(x -> {
      ticketsDTO.add(ticketToTicketDTO(x));
    } );
    return ticketsDTO;
  }
}

