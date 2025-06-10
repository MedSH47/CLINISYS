package com.csys.template.service;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketDTO;
import com.csys.template.factory.TicketFactory;
import com.csys.template.log.service.LogService;
import com.csys.template.repository.TicketRepository;
import com.csys.template.util.Helper;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {
    private final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;
    private final LogService logService;

    public TicketService(TicketRepository ticketRepository, LogService logService) {
        this.ticketRepository = ticketRepository;
        this.logService = logService;
    }

    public TicketDTO save(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket: {}", ticketDTO);
        Ticket ticket = TicketFactory.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        TicketDTO resultDTO = TicketFactory.toDTO(ticket);
        return resultDTO;
    }

public TicketDTO update(TicketDTO ticketDTO) {
    log.debug("Request to update Ticket: {}", ticketDTO);

    Ticket existingTicket = ticketRepository.findById(ticketDTO.getId())
            .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));

    boolean hasNewUser = existingTicket.getIdUtilisateur() == null && ticketDTO.getIdUtilisateur() != null;
    boolean hasNewModule = existingTicket.getModule() == null && ticketDTO.getIdModule() != null;

    if (hasNewUser && !hasNewModule) {
        logService.logTicketReview(existingTicket, true, null);
    } else if (!hasNewUser && hasNewModule) {
        logService.logTicketReview(existingTicket, null, true);
    } else if (hasNewUser && hasNewModule) {
        logService.logTicketReview(existingTicket, true, true);
    }

    // Convert DTO to entity (new entity with updated fields)
    Ticket updatedTicket = TicketFactory.toEntity(ticketDTO);

    Helper.mergeNonNullFields(updatedTicket, existingTicket);

    // Save the updated existingTicket (with merged fields)
    ticketRepository.save(existingTicket);

    return TicketFactory.toDTO(existingTicket);
}



    @Transactional(readOnly = true)
    public TicketDTO findOne(Integer id) {
        log.debug("Request to get Ticket: {}", id);
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        TicketDTO dto = TicketFactory.toDTO(ticket);
        return dto;
    }

  
  @Transactional(
      readOnly = true
  )
  public Ticket findTicket(Integer id) {
    log.debug("Request to get Ticket: {}",id);
    Ticket ticket= ticketRepository.findById(id).orElse(null);
    return ticket;
  }

  /**
   * Get all the tickets.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<TicketDTO> findAll() {
    log.debug("Request to get All Tickets");
    Collection<Ticket> result= ticketRepository.findAll();
    return TicketFactory.toDTOs(result);
  }

  /**
   * Delete ticket by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Ticket: {}",id);
    ticketRepository.deleteById(id);
  }
}

