package com.csys.template.web.rest.ressource;

import com.csys.template.dto.TicketDTO;
import com.csys.template.service.TicketService;
import java.lang.Integer;
import com.csys.template.util.RestPreconditions;

import java.lang.String;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Ticket.
 */
@RestController
@RequestMapping("/api")
public class TicketResource {
  private static final String ENTITY_NAME = "ticket";

  private final TicketService ticketService;

  private final Logger log = LoggerFactory.getLogger(TicketService.class);

  public TicketResource(TicketService ticketService) {
    this.ticketService=ticketService;
  }

  /**
   * POST  /tickets : Create a new ticket.
   *
   * @param ticketDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new ticket, or with status 400 (Bad Request) if the ticket has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/tickets")
  public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Ticket : {}", ticketDTO);
    if ( ticketDTO.getId() != null) {
      bindingResult.addError( new FieldError("TicketDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    TicketDTO result = ticketService.save(ticketDTO);
    return ResponseEntity.created( new URI("/api/tickets/"+ result.getId())).body(result);
  }

  /**
   * PUT  /tickets : Updates an existing ticket.
   *
   * @param id
   * @param ticketDTO the ticket to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated ticket,
   * or with status 400 (Bad Request) if the ticket is not valid,
   * or with status 500 (Internal Server Error) if the ticket couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/tickets/{id}")
  public ResponseEntity<TicketDTO> updateTicket(@PathVariable Integer id, @Valid @RequestBody TicketDTO ticketDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Ticket: {}",id);
    ticketDTO.setId(id);
    TicketDTO result =ticketService.update(ticketDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /tickets/{id} : get the "id" ticket.
   *
   * @param id the id of the ticket to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of ticket, or with status 404 (Not Found)
   */
  @GetMapping("/tickets/{id}")
  public ResponseEntity<TicketDTO> getTicket(@PathVariable Integer id) {
    log.debug("Request to get Ticket: {}",id);
    TicketDTO dto = ticketService.findOne(id);
    RestPreconditions.checkFound(dto, "ticket.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /tickets : get all the tickets.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of tickets in body
   */
  @GetMapping("/tickets")
  public Collection<TicketDTO> getAllTickets() {
    log.debug("Request to get all  Tickets : {}");
    return ticketService.findAll();
  }

  /**
   * DELETE  /tickets/{id} : delete the "id" ticket.
   *
   * @param id the id of the ticket to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/tickets/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
    log.debug("Request to delete Ticket: {}",id);
    ticketService.delete(id);
    return ResponseEntity.ok().build();
  }
}

