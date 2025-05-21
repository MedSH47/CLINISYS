package com.csys.template.factory;

import com.csys.template.domain.Avancement;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.Ticketfichier;
import com.csys.template.dto.AvancementDTO;
import com.csys.template.dto.TicketDTO;
import com.csys.template.dto.TicketfichierDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicketFactory {
  public static TicketDTO ticketToTicketDTO(Ticket ticket) {
    TicketDTO ticketDTO=new TicketDTO();
    ticketDTO.setId(ticket.getId());
    ticketDTO.setIdTicketParent(ticket.getIdTicketParent());
    ticketDTO.setTitre(ticket.getTitre());
    ticketDTO.setDescription(ticket.getDescription());
    ticketDTO.setUserCreation(ticket.getUserCreation());
    ticketDTO.setDateCreation(ticket.getDateCreation());
    ticketDTO.setStatue(ticket.getStatue());
    Set<AvancementDTO> avancementCollectionDtos = new HashSet<>();
    ticket.getAvancementCollection().forEach(x -> {
      AvancementDTO avancementDto = new AvancementDTO();
      avancementDto = AvancementFactory.avancementToAvancementDTO(x);
      avancementCollectionDtos.add(avancementDto);
    } );
    if(ticketDTO.getAvancementCollection() !=null) {
      ticketDTO.getAvancementCollection().clear();
      ticketDTO.getAvancementCollection().addAll(avancementCollectionDtos);
    }
    else {
      ticketDTO.setAvancementCollection(avancementCollectionDtos);
    }
    ticketDTO.setIdClient(ticket.getIdClient());
    ticketDTO.setIdModule(ticket.getIdModule());
    Set<TicketfichierDTO> ticketfichierCollectionDtos = new HashSet<>();
    ticket.getTicketfichierCollection().forEach(x -> {
      TicketfichierDTO ticketfichierDto = new TicketfichierDTO();
      ticketfichierDto = TicketfichierFactory.ticketfichierToTicketfichierDTO(x);
      ticketfichierCollectionDtos.add(ticketfichierDto);
    } );
    if(ticketDTO.getTicketfichierCollection() !=null) {
      ticketDTO.getTicketfichierCollection().clear();
      ticketDTO.getTicketfichierCollection().addAll(ticketfichierCollectionDtos);
    }
    else {
      ticketDTO.setTicketfichierCollection(ticketfichierCollectionDtos);
    }
    return ticketDTO;
  }

  public static Ticket ticketDTOToTicket(TicketDTO ticketDTO) {
    Ticket ticket=new Ticket();
    ticket.setId(ticketDTO.getId());
    ticket.setIdTicketParent(ticketDTO.getIdTicketParent());
    ticket.setTitre(ticketDTO.getTitre());
    ticket.setDescription(ticketDTO.getDescription());
    ticket.setUserCreation(ticketDTO.getUserCreation());
    ticket.setDateCreation(ticketDTO.getDateCreation());
    ticket.setStatue(ticketDTO.getStatue());
    Set<Avancement> avancementCollections = new HashSet<>();
    ticketDTO.getAvancementCollection().forEach(x -> {
      Avancement avancement = new Avancement();
      avancement = AvancementFactory.avancementDTOToAvancement(x);
      avancementCollections.add(avancement);
    } );
    if(ticket.getAvancementCollection() !=null) {
      ticket.getAvancementCollection().clear();
      ticket.getAvancementCollection().addAll(avancementCollections);
    }
    else {
      ticket.setAvancementCollection(avancementCollections);
    }
    ticket.setIdClient(ticketDTO.getIdClient());
    ticket.setIdModule(ticketDTO.getIdModule());
    Set<Ticketfichier> ticketfichierCollections = new HashSet<>();
    ticketDTO.getTicketfichierCollection().forEach(x -> {
      Ticketfichier ticketfichier = new Ticketfichier();
      ticketfichier = TicketfichierFactory.ticketfichierDTOToTicketfichier(x);
      ticketfichierCollections.add(ticketfichier);
    } );
    if(ticket.getTicketfichierCollection() !=null) {
      ticket.getTicketfichierCollection().clear();
      ticket.getTicketfichierCollection().addAll(ticketfichierCollections);
    }
    else {
      ticket.setTicketfichierCollection(ticketfichierCollections);
    }
    return ticket;
  }

  public static Collection<TicketDTO> ticketToTicketDTOs(Collection<Ticket> tickets) {
    List<TicketDTO> ticketsDTO=new ArrayList<>();
    tickets.forEach(x -> {
      ticketsDTO.add(ticketToTicketDTO(x));
    } );
    return ticketsDTO;
  }


  public static TicketDTO lazyticketToTicketDTO(Ticket ticket) {
    TicketDTO ticketDTO=new TicketDTO();
    ticketDTO.setId(ticket.getId());
    ticketDTO.setIdTicketParent(ticket.getIdTicketParent());
    ticketDTO.setTitre(ticket.getTitre());
    ticketDTO.setDescription(ticket.getDescription());
    ticketDTO.setUserCreation(ticket.getUserCreation());
    ticketDTO.setDateCreation(ticket.getDateCreation());
    ticketDTO.setStatue(ticket.getStatue());
    ticketDTO.setIdClient(ticket.getIdClient());
    ticketDTO.setIdModule(ticket.getIdModule());
    return ticketDTO;
  }

  public static Collection<TicketDTO> lazyticketToTicketDTOs(Collection<Ticket> tickets) {
    List<TicketDTO> ticketsDTO=new ArrayList<>();
    tickets.forEach(x -> {
      ticketsDTO.add(lazyticketToTicketDTO(x));
    } );
    return ticketsDTO;
  }
}

