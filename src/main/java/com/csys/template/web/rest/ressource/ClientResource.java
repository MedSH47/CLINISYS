package com.csys.template.web.rest.ressource;

import com.csys.template.dto.ClientDTO;
import com.csys.template.service.ClientService;
import com.csys.template.service.CustomUserDetailsService;
import com.csys.template.service.JwtUtil;
import com.csys.template.util.RestPreconditions;

import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;


import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ClientResource {

  

  private final ClientService clientService;

  private final Logger log = LoggerFactory.getLogger(ClientService.class);
  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;

  public ClientResource(ClientService clientService,JwtUtil jwtUtil,CustomUserDetailsService customUserDetailsService) {
    this.clientService=clientService;
    this.jwtUtil=jwtUtil;
    this.customUserDetailsService=customUserDetailsService;
  }

  @PostMapping("/clients")
  public ResponseEntity<?> createClient(@Valid @RequestBody ClientDTO clientDTO, BindingResult bindingResult,
  @RequestHeader(value = "Authorization") String authorizationHeader) throws URISyntaxException, MethodArgumentNotValidException {
    try {
      String token = authorizationHeader.substring(7);
      String username = jwtUtil.extractUsername(token);
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_Admin"));
      if (isAdmin) {
        clientDTO.setCreationUser(username);
        ClientDTO result=clientService.save(clientDTO);
        return ResponseEntity.created( new URI("/api/clients/"+ result.getId())).body(result);
      }
      else{
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin Action!");
      }
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(clientDTO);
    }
  }
  @PutMapping("/clients/{id}")
  public ResponseEntity<ClientDTO> updateClient(@PathVariable Integer id, @Valid @RequestBody ClientDTO clientDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Client: {}",id);
    clientDTO.setId(id);
    ClientDTO result =clientService.update(clientDTO);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/clients/{id}")
  public ResponseEntity<ClientDTO> getClient(@PathVariable Integer id) {
    log.debug("Request to get Client: {}",id);
    ClientDTO dto = clientService.findOne(id);
    RestPreconditions.checkFound(dto, "client.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping("/clients")
  public Collection<ClientDTO> getAllClients() {
    log.debug("Request to get all  Clients : {}");
    return clientService.findAll();
  }

  @DeleteMapping("/clients/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
    log.debug("Request to delete Client: {}",id);
    clientService.delete(id);
    return ResponseEntity.ok().build();
  }
}

