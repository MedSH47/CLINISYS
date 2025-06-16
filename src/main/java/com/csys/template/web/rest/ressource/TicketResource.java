package com.csys.template.web.rest.ressource;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.service.TicketService;
import com.csys.template.util.RestPreconditions;
import com.csys.template.web.rest.errors.ErrorResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);
    private final TicketService ticketService;

    public TicketResource(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/tickets")
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketRequestDTO ticketRequestDTO)
            throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketRequestDTO);
        TicketResponseDTO result = ticketService.save(ticketRequestDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId())).body(result);
    }

    @PutMapping("tickets/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Integer id, @RequestBody TicketRequestDTO ticketRequestDTO,
            HttpServletRequest request) {
        try {
            TicketResponseDTO result = ticketService.update(id, ticketRequestDTO);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Validation Error",
                    e.getMessage(), 
                    request.getRequestURI());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
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
            @RequestParam(required = false) Priorite priorite,
            @RequestParam(required = false) Boolean[] actifs) {
        log.debug("Request to get all Tickets with filters");
        return ticketService.findAll(statue, idModule, priorite,actifs);
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Integer id) {
        log.debug("Request to delete Ticket: {}", id);
        return ticketService.delete(id);
    }

    @GetMapping("/tickets/parents")
    public List<TicketResponseDTO> getAllParentsTickets() {
        return ticketService.findAllParents();
    }
    
}