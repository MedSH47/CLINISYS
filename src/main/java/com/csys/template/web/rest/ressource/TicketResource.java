package com.csys.template.web.rest.ressource;

import com.csys.template.domain.enum_identifier.Status;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    this.ticketService = ticketService;
  }

  @PostMapping("/tickets")
  public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Ticket : {}", ticketDTO);
    if (ticketDTO.getId() != null) {
      bindingResult.addError(new FieldError("TicketDTO", "id", "POST method does not accepte " + ENTITY_NAME + " with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    TicketDTO result = ticketService.save(ticketDTO);
    return ResponseEntity.created(new URI("/api/tickets/" + result.getId())).body(result);
  }

  @PutMapping("/tickets/{id}")
  public ResponseEntity<TicketDTO> updateTicket(@PathVariable Integer id, @Valid @RequestBody TicketDTO ticketDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Ticket: {}", id);
    ticketDTO.setId(id);
    TicketDTO result = ticketService.update(ticketDTO);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/tickets/{id}")
  public ResponseEntity<TicketDTO> getTicket(@PathVariable Integer id) {
    log.debug("Request to get Ticket: {}", id);
    TicketDTO dto = ticketService.findOne(id);
    RestPreconditions.checkFound(dto, "ticket.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping("/tickets")
  public Collection<TicketDTO> getAllTickets(@RequestParam(required = false) Status statue,
                                           @RequestParam(required = false) Integer idModule,
                                           @RequestParam(required = false) String priorite) {
    log.debug("Request to get all Tickets with filters");
    return ticketService.findAll(statue, idModule, priorite);
  }

  @DeleteMapping("/tickets/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
    log.debug("Request to delete Ticket: {}", id);
    ticketService.delete(id);
    return ResponseEntity.ok().build();
  }
}