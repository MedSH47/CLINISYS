package com.csys.template.web.rest.ressource;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.service.TicketService;
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
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);
    private final TicketService ticketService;

    public TicketResource(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/tickets")
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketRequestDTO ticketRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketRequestDTO);
        TicketResponseDTO result = ticketService.save(ticketRequestDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId())).body(result);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable Integer id, @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to update Ticket: {}", id);
        TicketResponseDTO result = ticketService.update(id, ticketRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketResponseDTO> getTicket(@PathVariable Integer id) {
        log.debug("Request to get Ticket: {}", id);
        TicketResponseDTO dto = ticketService.findOne(id);
        RestPreconditions.checkFound(dto, "ticket.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/tickets")
    public List<TicketResponseDTO> getAllTickets(@RequestParam(required = false) Status statue,
                                                 @RequestParam(required = false) Integer idModule,
                                                 @RequestParam(required = false) Priorite priorite) {
        log.debug("Request to get all Tickets with filters");
        return ticketService.findAll(statue, idModule, priorite);
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        log.debug("Request to delete Ticket: {}", id);
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}