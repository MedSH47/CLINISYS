package com.csys.template.web.rest.ressource;

import com.csys.template.dtoRequest.ClientRequestDTO;
import com.csys.template.dtoResponse.ClientResponseDTO;
import com.csys.template.service.ClientService;
import com.csys.template.util.RestPreconditions;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClientResource {
    private final Logger log = LoggerFactory.getLogger(ClientResource.class);
    private final ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Client : {}", clientRequestDTO);
        ClientResponseDTO result = clientService.save(clientRequestDTO);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId())).body(result);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Integer id, @Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        log.debug("REST request to update Client : {}", id);
        ClientResponseDTO result = clientService.update(id, clientRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable Integer id) {
        log.debug("REST request to get Client : {}", id);
        ClientResponseDTO dto = clientService.findOne(id);
        RestPreconditions.checkFound(dto, "client.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/clients")
    public List<ClientResponseDTO> getAllClients() {
        log.debug("Request to get all Clients");
        return clientService.findAll();
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        log.debug("Request to delete Client: {}", id);
        clientService.delete(id);
        return ResponseEntity.noContent().build(); // Changed to 204 No Content for better practice
    }
}