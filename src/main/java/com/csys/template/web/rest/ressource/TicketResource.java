package com.csys.template.web.rest.ressource;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoProjection.*; // Importation des nouveaux DTOs de projection
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

    // --- Les endpoints POST, PUT, GET par ID, etc. ne changent pas ---
    @PostMapping("/tickets")
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketRequestDTO ticketRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketRequestDTO);
        TicketResponseDTO result = ticketService.save(ticketRequestDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId())).body(result);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable Integer id, @RequestBody TicketRequestDTO ticketRequestDTO) {
        TicketResponseDTO result = ticketService.update(id, ticketRequestDTO);
        return ResponseEntity.ok(result);
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
        return ticketService.findAll(statue, idModule, priorite, actifs);
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


    // --- Endpoints de statistiques refactorisés ---

    /**
     * CORRIGÉ : Retourne maintenant une liste de StatusCountDTO.
     */
    @GetMapping("/tickets/stats/by-status")
    public ResponseEntity<List<StatusCountDTO>> getTicketCountsByStatus() {
        log.debug("REST request to get ticket counts by status");
        List<StatusCountDTO> counts = ticketService.getCountsByStatus();
        return ResponseEntity.ok().body(counts);
    }

    /**
     * CORRIGÉ : Retourne maintenant une liste de TicketCalendarEventDTO.
     */
    @GetMapping("/tickets/calendar-events")
    public ResponseEntity<List<TicketCalendarEventDTO>> getTicketCalendarEvents() {
        log.debug("REST request to get calendar events");
        List<TicketCalendarEventDTO> data = ticketService.getCalendarEvents();
        return ResponseEntity.ok(data);
    }
    
    /**
     * CORRIGÉ : Retourne maintenant un objet GlobalTicketCountDTO.
     */
    @GetMapping("/tickets/stats/global-counts")
    public ResponseEntity<GlobalTicketCountDTO> getGlobalTicketCounts() {
        log.debug("REST request to get global ticket counts");
        GlobalTicketCountDTO counts = ticketService.getGlobalTicketCounts();
        return ResponseEntity.ok().body(counts);
    }

    /**
     * CORRIGÉ : Retourne maintenant une liste de ActiveTicketCountDTO.
     */
    @GetMapping("/tickets/stats/active-by-category")
    public ResponseEntity<List<ActiveTicketCountDTO>> getActiveTicketsByCategory(
            @RequestParam(name = "groupBy", defaultValue = "employee") String groupBy) {
        log.debug("REST request to get active ticket counts by category: {}", groupBy);
        List<ActiveTicketCountDTO> data = ticketService.getActiveTicketsByAssigneeOrModule(groupBy);
        return ResponseEntity.ok(data);
    }

    /**
     * CORRIGÉ : Retourne maintenant une liste de PerformanceStatDTO.
     */
    @GetMapping("/tickets/stats/performance")
    public ResponseEntity<List<PerformanceStatDTO>> getPerformanceStats(
            @RequestParam(name = "groupBy", defaultValue = "employee") String groupBy,
            @RequestParam(name = "period", defaultValue = "current_month") String period) {
        log.debug("REST request to get performance stats by {} for period: {}", groupBy, period);
        List<PerformanceStatDTO> data = ticketService.getPerformanceStats(groupBy, period);
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/tickets/overdue")
    public ResponseEntity<List<TicketResponseDTO>> getOverdueTickets() {
        log.debug("REST request to get overdue tickets");
        List<TicketResponseDTO> data = ticketService.getOverdueTickets();
        return ResponseEntity.ok(data);
    }
}