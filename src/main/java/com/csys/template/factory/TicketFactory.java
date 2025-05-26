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
    ticketDTO.setChildTickets(ticket.getChildTickets());
    ticketDTO.setParentTicket(ticket.getParentTicket());
    ticketDTO.setTitre(ticket.getTitre());
    ticketDTO.setDescription(ticket.getDescription());
    ticketDTO.setUserCreation(ticket.getUserCreation());
    ticketDTO.setDateCreation(ticket.getDateCreation());
    ticketDTO.setPriorite(ticket.getPriorite());
    ticketDTO.setStatue(ticket.getStatue());
    ticketDTO.setAvancementSet(ticket.getAvancementSet());
    ticketDTO.setIdClient(ticket.getIdClient());
    ticketDTO.setIdModule(ModuleFactory.moduleToModuleDTO( ticket.getIdModule()));
    ticketDTO.setIdUtilisateur(UtilisateurFactory.utilisateurToUtilisateurDTO(ticket.getIdUtilisateur(),true));
    ticketDTO.setDocumentJointesSet(ticket.getDocumentJointesSet());
    ticketDTO.setCommentaireSet(ticket.getCommentaireSet());
    ticketDTO.setTicketfichierSet(ticket.getTicketfichierSet());
    return ticketDTO;
  }

  public static Ticket ticketDTOToTicket(TicketDTO ticketDTO) {
    Ticket ticket=new Ticket();
    ticket.setId(ticketDTO.getId());
    
    ticket.setTitre(ticketDTO.getTitre());
    ticket.setDescription(ticketDTO.getDescription());
    ticket.setUserCreation(ticketDTO.getUserCreation());
    ticket.setDateCreation(ticketDTO.getDateCreation());
    ticket.setPriorite(ticketDTO.getPriorite());
    ticket.setStatue(ticketDTO.getStatue());
    ticket.setChildTickets(ticketDTO.getChildTickets());
    ticket.setParentTicket(ticketDTO.getParentTicket());
    ticket.setAvancementSet(ticketDTO.getAvancementSet());
    ticket.setIdClient(ticketDTO.getIdClient());
    ticket.setIdModule(ModuleFactory.moduleDTOToModule( ticketDTO.getIdModule()));
    ticket.setIdUtilisateur(UtilisateurFactory.utilisateurDTOToUtilisateur( ticketDTO.getIdUtilisateur()));
    ticket.setDocumentJointesSet(ticketDTO.getDocumentJointesSet());
    ticket.setCommentaireSet(ticketDTO.getCommentaireSet());
    ticket.setTicketfichierSet(ticketDTO.getTicketfichierSet());
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

