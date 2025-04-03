package com.csys.template.web.rest.ressource;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketDto;
import com.csys.template.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/Ticket")
public class TicketRessource {

    @Autowired
    private TicketService ticketService;
    private static final String ENTITY_NAME = "Ticket";

    @PostMapping
    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket entity, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
        if (entity.getId() != null) {
            bindingResult.addError(new FieldError(ENTITY_NAME, "Id", "Post not allowed Ticket with Id"));
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        Ticket ticket = ticketService.addTicket(entity);
        return ResponseEntity.created(new URI("/api/Ticket/" + ticket.getId())).body(ticket);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PutMapping
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket entity) throws URISyntaxException {
        Ticket ticket = ticketService.updateTicket(entity);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{id}")
    public TicketDto findOne(@PathVariable Integer id) {
        return ticketService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }
}
