package com.csys.template.web.rest.ressource;
import com.csys.template.util.RestPreconditions;

import com.csys.template.dto.TicketfichierDTO;
import com.csys.template.service.TicketfichierService;
import java.lang.Integer;
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
 * REST controller for managing Ticketfichier.
 */
@RestController
@RequestMapping("/api")
public class TicketfichierResource {
  private static final String ENTITY_NAME = "ticketfichier";

  private final TicketfichierService ticketfichierService;

  private final Logger log = LoggerFactory.getLogger(TicketfichierService.class);

  public TicketfichierResource(TicketfichierService ticketfichierService) {
    this.ticketfichierService=ticketfichierService;
  }

  /**
   * POST  /ticketfichiers : Create a new ticketfichier.
   *
   * @param ticketfichierDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new ticketfichier, or with status 400 (Bad Request) if the ticketfichier has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/ticketfichiers")
  public ResponseEntity<TicketfichierDTO> createTicketfichier(@Valid @RequestBody TicketfichierDTO ticketfichierDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Ticketfichier : {}", ticketfichierDTO);
    if ( ticketfichierDTO.getId() != null) {
      bindingResult.addError( new FieldError("TicketfichierDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    TicketfichierDTO result = ticketfichierService.save(ticketfichierDTO);
    return ResponseEntity.created( new URI("/api/ticketfichiers/"+ result.getId())).body(result);
  }

  /**
   * PUT  /ticketfichiers : Updates an existing ticketfichier.
   *
   * @param id
   * @param ticketfichierDTO the ticketfichier to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated ticketfichier,
   * or with status 400 (Bad Request) if the ticketfichier is not valid,
   * or with status 500 (Internal Server Error) if the ticketfichier couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/ticketfichiers/{id}")
  public ResponseEntity<TicketfichierDTO> updateTicketfichier(@PathVariable Integer id, @Valid @RequestBody TicketfichierDTO ticketfichierDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Ticketfichier: {}",id);
    ticketfichierDTO.setId(id);
    TicketfichierDTO result =ticketfichierService.update(ticketfichierDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /ticketfichiers/{id} : get the "id" ticketfichier.
   *
   * @param id the id of the ticketfichier to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of ticketfichier, or with status 404 (Not Found)
   */
  @GetMapping("/ticketfichiers/{id}")
  public ResponseEntity<TicketfichierDTO> getTicketfichier(@PathVariable Integer id) {
    log.debug("Request to get Ticketfichier: {}",id);
    TicketfichierDTO dto = ticketfichierService.findOne(id);
    RestPreconditions.checkFound(dto, "ticketfichier.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /ticketfichiers : get all the ticketfichiers.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of ticketfichiers in body
   */
  @GetMapping("/ticketfichiers")
  public Collection<TicketfichierDTO> getAllTicketfichiers() {
    log.debug("Request to get all  Ticketfichiers : {}");
    return ticketfichierService.findAll();
  }

  /**
   * DELETE  /ticketfichiers/{id} : delete the "id" ticketfichier.
   *
   * @param id the id of the ticketfichier to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/ticketfichiers/{id}")
  public ResponseEntity<Void> deleteTicketfichier(@PathVariable Integer id) {
    log.debug("Request to delete Ticketfichier: {}",id);
    ticketfichierService.delete(id);
    return ResponseEntity.ok().build();
  }
}

