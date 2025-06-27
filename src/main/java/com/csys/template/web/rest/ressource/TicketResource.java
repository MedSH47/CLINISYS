package com.csys.template.web.rest.ressource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.service.TicketService;
import com.csys.template.util.RestPreconditions;
import com.csys.template.web.rest.errors.ErrorResponse;


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

    @GetMapping("/tickets/stats/by-status")
    public ResponseEntity<Map<String, Long>> getTicketCountsByStatus() {
        log.debug("REST request to get ticket counts by status");
        // Appelle la méthode du service pour obtenir la Map<Status, Long>
        Map<Status, Long> countsByEnum = ticketService.getCountsByStatus();
        
        // Convertit les clés d'énumération (Status) en String pour la réponse JSON
        // Le frontend attend "En_attente", "En_cours", etc. comme clés.
        Map<String, Long> counts = countsByEnum.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));
        
        return ResponseEntity.ok().body(counts);
    }

    @GetMapping("/tickets/calendar-events")
    public ResponseEntity<List<Map<String, Object>>> getTicketCalendarEvents() {
        log.debug("REST request to get calendar events");
        List<Map<String, Object>> data = ticketService.getCalendarEvents();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/tickets/stats/global-counts")
    public ResponseEntity<Map<String, Long>> getGlobalTicketCounts() {
        log.debug("REST request to get global ticket counts");
        Map<String, Long> counts = ticketService.getGlobalTicketCounts();
        return ResponseEntity.ok().body(counts);
    }

    @GetMapping("/tickets/stats/active-by-category")
    public ResponseEntity<List<Map<String, Object>>> getActiveTicketsByCategory(
            @RequestParam(name = "groupBy", defaultValue = "employee") String groupBy) {
        log.debug("REST request to get active ticket counts by category: {}", groupBy);
        List<Map<String, Object>> data = ticketService.getActiveTicketsByAssigneeOrModule(groupBy);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/tickets/stats/performance")
    public ResponseEntity<List<Map<String, Object>>> getPerformanceStats(
            @RequestParam(name = "groupBy", defaultValue = "employee") String groupBy,
            @RequestParam(name = "period", defaultValue = "current_month") String period) {
        log.debug("REST request to get performance stats by {} for period: {}", groupBy, period);
        List<Map<String, Object>> data = ticketService.getPerformanceStats(groupBy, period);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/tickets/overdue")
    public ResponseEntity<List<TicketResponseDTO>> getOverdueTickets() {
        log.debug("REST request to get overdue tickets");
        List<TicketResponseDTO> data = ticketService.getOverdueTickets();
        return ResponseEntity.ok(data);
    }
}