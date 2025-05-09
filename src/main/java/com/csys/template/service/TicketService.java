package com.csys.template.service;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketDTO;
import com.csys.template.factory.TicketFactory;
import com.csys.template.repository.TicketRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Ticket.
 */
@Service
@Transactional
public class TicketService {
  private final Logger log = LoggerFactory.getLogger(TicketService.class);

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository=ticketRepository;
  }

  /**
   * Save a ticketDTO.
   *
   * @param ticketDTO
   * @return the persisted entity
   */
  public TicketDTO save(TicketDTO ticketDTO) {
    log.debug("Request to save Ticket: {}",ticketDTO);
    Ticket ticket = TicketFactory.ticketDTOToTicket(ticketDTO);
    ticket = ticketRepository.save(ticket);
    TicketDTO resultDTO = TicketFactory.ticketToTicketDTO(ticket);
    return resultDTO;
  }

  /**
   * Update a ticketDTO.
   *
   * @param ticketDTO
   * @return the updated entity
   */
  public ResponseEntity< ?> update(TicketDTO ticketDTO) {
    log.debug("Request to update Ticket: {}", ticketDTO);
    
    // Check if ticket exists
    Ticket inBase = ticketRepository.findById(ticketDTO.getId())
        .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
    
    // Convert DTO to entity
    Ticket ticket = TicketFactory.ticketDTOToTicket(ticketDTO);
    
    // Validate collaborator assignment
    if (ticket.getCollaborateur() != null) {
        Optional<Ticket> existingTicket = ticketRepository.findByCollaborateurAndIdNot(
            ticket.getCollaborateur(), 
            ticket.getId()
        );
        
        if (existingTicket.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Collaborator is already assigned to ticket ID: " + existingTicket.get().getId());
        }
    }
    
    // Save the updated ticket
    ticket = ticketRepository.save(ticket);
    return ResponseEntity.ok().body(TicketFactory.ticketToTicketDTO(ticket)) ;
}
  /**
   * Get one ticketDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public TicketDTO findOne(Integer id) {
    log.debug("Request to get Ticket: {}",id);
    Ticket ticket= ticketRepository.findById(id).orElse(null);
    TicketDTO dto = TicketFactory.ticketToTicketDTO(ticket);
    return dto;
  }

  /**
   * Get one ticket by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
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
    return TicketFactory.ticketToTicketDTOs(result);
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

