package com.csys.template.service;

import com.csys.template.AI_Search_Box.AiQueryResponse;
import com.csys.template.AI_Search_Box.TicketSpecification;
import com.csys.template.domain.QTicket;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.factory.TicketFactory;
import com.csys.template.log.service.LogService;
import com.csys.template.repository.TicketRepository;
import com.csys.template.util.WhereClauseBuilder;

import liquibase.pro.packaged.he;
import liquibase.pro.packaged.js;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.csys.template.util.Helper;;

@Service
@Transactional
public class TicketService {
    private final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final LogService logService;
    private final RestTemplate restTemplate; // <-- ADD THIS LINE to declare the field


    public TicketService(TicketRepository ticketRepository, LogService logService,RestTemplate restTemplate) {
        this.ticketRepository = ticketRepository;
        this.logService = logService;
        this.restTemplate= restTemplate;
    }

    public TicketResponseDTO save(TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to save Ticket: {}", ticketRequestDTO);
        Ticket ticket = TicketFactory.toEntity(ticketRequestDTO);
        ticket = ticketRepository.save(ticket);
        return TicketFactory.toResponseDTO(ticket);
    }

    public TicketResponseDTO update(Integer ticketId, TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to update Ticket: {}", ticketId);
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
        if (ticketRequestDTO.getIdModule()!=null && existingTicket.getModule()==null ) {
            logService.logTicketReview(existingTicket, false, true);
        }
        if (ticketRequestDTO.getIdUtilisateur()!=null && existingTicket.getIdUtilisateur()==null) {
            logService.logTicketReview(existingTicket, true, true);
        }
        TicketFactory.updateFromDTO(existingTicket, ticketRequestDTO);
        
        ticketRepository.save(existingTicket);
        return TicketFactory.toResponseDTO(existingTicket);
    }

    @Transactional(readOnly = true)
    public TicketResponseDTO findOne(Integer id) {
        log.debug("Request to get Ticket: {}", id);
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return TicketFactory.toResponseDTO(ticket);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> findAll(Status statue, Integer idModule, Priorite priorite, Boolean[] actifs) {
        log.debug("Request to get All Tickets with filters");
        QTicket qTicket = QTicket.ticket;
        WhereClauseBuilder builder = new WhereClauseBuilder()
                .optionalAnd(statue, () -> qTicket.statue.eq(statue))
                .optionalAnd(idModule, () -> qTicket.module().id.eq(idModule))
                .optionalAnd(actifs, () -> qTicket.actif.in(actifs))
                .optionalAnd(priorite, () -> qTicket.priorite.eq(priorite));

        List<Ticket> result = (List<Ticket>) ticketRepository.findAll(builder);
        return TicketFactory.toResponseDTOs(result);
    }

    public ResponseEntity<?> delete(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
        if (ticket.getModule() != null) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(java.util.Map.of("message", "ticket " + ticket.getId() + " cannot Delete With Module"));
        }
        if (ticket.getIdUtilisateur() != null) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(java.util.Map.of("message", "ticket " + ticket.getId() + " cannot Delete With User"));
        }
        TicketResponseDTO ticketResponseDTO = TicketFactory.toResponseDTO(ticket);
        if (!ticketResponseDTO.getChildTickets().isEmpty()) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(java.util.Map.of("message",
                            "ticket " + ticketResponseDTO.getId() + " cannot Delete With Child Tickets"));
        }
        log.debug("Request to delete Ticket: {}", id);
        ticketRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public List<TicketResponseDTO> findAllParents() {
        List<Ticket> tickets = ticketRepository.findAll();
        return TicketFactory.toResponseDTOsParents(tickets);
    }

  public List<TicketResponseDTO> searchByNaturalLanguage(String query) {
    // Define the AI service URL
    String aiServiceUrl = "http://localhost:5001/parse-query";

    // Create the request body for the AI service
    Map<String, String> requestBody = Collections.singletonMap("query", query);

    // Call the Flask AI service
    // Note: AiQueryResponse now uses 'entityType' instead of 'intent'
    AiQueryResponse aiResponse = restTemplate.postForObject(aiServiceUrl, requestBody, AiQueryResponse.class);

    // The logic to decide what to do based on the response is now in AiSearchService.
    // This example assumes you might still want a ticket-specific search.
    if (aiResponse != null && "ticket".equals(aiResponse.getEntityType())) {
        // Build the specification from the entities
        Specification<Ticket> spec = TicketSpecification.findByEntities(aiResponse.getEntities());

        // Execute the query and map to DTOs
        return ticketRepository.findAll(spec).stream()
                .map(TicketFactory::toResponseDTO) 
                .collect(Collectors.toList());
    }

    // Return empty list if intent is not recognized or AI service fails
    return Collections.emptyList();
}
}