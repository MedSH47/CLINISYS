package com.csys.template.web.rest.ressource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDto;
import com.csys.template.service.ClientService;

@RestController
@RequestMapping("/api/Client")
public class ClientRessource {

    @Autowired
    private ClientService clientService;
    private static final String ENTITY_NAME = "Client";

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client entity, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
        if (entity.getId() != null) {
            bindingResult.addError(new FieldError(ENTITY_NAME, "Id", "Post not allowed Client with Id"));
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        Client client = clientService.addClient(entity);
        return ResponseEntity.created(new URI("/api/Client/" + client.getId())).body(client);
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client entity) throws URISyntaxException {
        Client client = clientService.updateClient(entity);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}")
    public ClientDto findOne(@PathVariable Integer id) {
        return clientService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}
