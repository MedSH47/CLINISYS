package com.csys.template.web.rest.ressource;

import com.csys.template.dto.ClientDTO;
import com.csys.template.service.ClientService;
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
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {
  private static final String ENTITY_NAME = "client";

  private final ClientService clientService;

  private final Logger log = LoggerFactory.getLogger(ClientService.class);

  public ClientResource(ClientService clientService) {
    this.clientService=clientService;
  }

  /**
   * POST  /clients : Create a new client.
   *
   * @param clientDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new client, or with status 400 (Bad Request) if the client has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/clients")
  public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Client : {}", clientDTO);
    if ( clientDTO.getId() != null) {
      bindingResult.addError( new FieldError("ClientDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    ClientDTO result = clientService.save(clientDTO);
    return ResponseEntity.created( new URI("/api/clients/"+ result.getId())).body(result);
  }

  /**
   * PUT  /clients : Updates an existing client.
   *
   * @param id
   * @param clientDTO the client to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated client,
   * or with status 400 (Bad Request) if the client is not valid,
   * or with status 500 (Internal Server Error) if the client couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/clients/{id}")
  public ResponseEntity<ClientDTO> updateClient(@PathVariable Integer id, @Valid @RequestBody ClientDTO clientDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Client: {}",id);
    clientDTO.setId(id);
    ClientDTO result =clientService.update(clientDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /clients/{id} : get the "id" client.
   *
   * @param id the id of the client to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of client, or with status 404 (Not Found)
   */
  @GetMapping("/clients/{id}")
  public ResponseEntity<ClientDTO> getClient(@PathVariable Integer id) {
    log.debug("Request to get Client: {}",id);
    ClientDTO dto = clientService.findOne(id);
    RestPreconditions.checkFound(dto, "client.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /clients : get all the clients.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of clients in body
   */
  @GetMapping("/clients")
  public Collection<ClientDTO> getAllClients() {
    log.debug("Request to get all  Clients : {}");
    return clientService.findAll();
  }

  /**
   * DELETE  /clients/{id} : delete the "id" client.
   *
   * @param id the id of the client to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/clients/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
    log.debug("Request to delete Client: {}",id);
    clientService.delete(id);
    return ResponseEntity.ok().build();
  }
}

