package com.csys.template.service;

import com.csys.template.domain.QTicket;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.factory.TicketFactory;
import com.csys.template.repository.TicketRepository;
import com.csys.template.util.WhereClauseBuilder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {
    private final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
        
        // Create a temporary entity from the DTO to get related objects
        Ticket updatedData = TicketFactory.toEntity(ticketRequestDTO);
        
        existingTicket.setTitre(updatedData.getTitre());
        existingTicket.setDescription(updatedData.getDescription());
        existingTicket.setPriorite(updatedData.getPriorite());
        existingTicket.setStatue(updatedData.getStatue());
        existingTicket.setParentTicket(updatedData.getParentTicket());
        existingTicket.setIdClient(updatedData.getIdClient());
        existingTicket.setModule(updatedData.getModule());
        existingTicket.setIdUtilisateur(updatedData.getIdUtilisateur());

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
    public List<TicketResponseDTO> findAll(Status statue, Integer idModule, Priorite priorite) {
        log.debug("Request to get All Tickets with filters");
        QTicket qTicket = QTicket.ticket;
        WhereClauseBuilder builder = new WhereClauseBuilder()
                .optionalAnd(statue, () -> qTicket.statue.eq(statue))
                .optionalAnd(idModule, () -> qTicket.module().id.eq(idModule))
                .optionalAnd(priorite, () -> qTicket.priorite.eq(priorite));

        List<Ticket> result = (List<Ticket>) ticketRepository.findAll(builder);
        return TicketFactory.toResponseDTOs(result);
    }

    public void delete(Integer id) {
        log.debug("Request to delete Ticket: {}", id);
        ticketRepository.deleteById(id);
    }
}