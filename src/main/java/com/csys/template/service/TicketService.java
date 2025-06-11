package com.csys.template.service;

import com.csys.template.domain.QTicket;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dto.TicketDTO;
import com.csys.template.factory.TicketFactory;
import com.csys.template.log.service.LogService;
import com.csys.template.repository.TicketRepository;
import com.csys.template.util.Helper;
import com.csys.template.util.WhereClauseBuilder;
import java.util.Collection;
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
    private final LogService logService;

    public TicketService(TicketRepository ticketRepository, LogService logService) {
        this.ticketRepository = ticketRepository;
        this.logService = logService;
    }

    public TicketDTO save(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket: {}", ticketDTO);
        Ticket ticket = TicketFactory.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        TicketDTO resultDTO = TicketFactory.toDTO(ticket);
        return resultDTO;
    }

    public TicketDTO update(TicketDTO ticketDTO) {
        log.debug("Request to update Ticket: {}", ticketDTO);

        Ticket existingTicket = ticketRepository.findById(ticketDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));

        boolean hasNewUser = existingTicket.getIdUtilisateur() == null && ticketDTO.getIdUtilisateur() != null;
        boolean hasNewModule = existingTicket.getModule() == null && ticketDTO.getIdModule() != null;

        if (hasNewUser && !hasNewModule) {
            logService.logTicketReview(existingTicket, true, null);
        } else if (!hasNewUser && hasNewModule) {
            logService.logTicketReview(existingTicket, null, true);
        } else if (hasNewUser && hasNewModule) {
            logService.logTicketReview(existingTicket, true, true);
        }

        Ticket updatedTicket = TicketFactory.toEntity(ticketDTO);
        Helper.mergeNonNullFields(updatedTicket, existingTicket);
        ticketRepository.save(existingTicket);

        return TicketFactory.toDTO(existingTicket);
    }

    @Transactional(readOnly = true)
    public TicketDTO findOne(Integer id) {
        log.debug("Request to get Ticket: {}", id);
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        TicketDTO dto = TicketFactory.toDTO(ticket);
        return dto;
    }

    @Transactional(readOnly = true)
    public Ticket findTicket(Integer id) {
        log.debug("Request to get Ticket: {}", id);
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return ticket;
    }

    @Transactional(readOnly = true)
    public Collection<TicketDTO> findAll(Status statue, Integer idModule, String priorite) {
        log.debug("Request to get All Tickets with filters");
        QTicket qTicket = QTicket.ticket;
        WhereClauseBuilder builder = new WhereClauseBuilder()
                .optionalAnd(statue, () -> qTicket.statue.eq(statue))
                .optionalAnd(idModule, () -> qTicket.module().id.eq(idModule))
                .optionalAnd(priorite, () -> qTicket.priorite.equalsIgnoreCase(priorite));
        
        List<Ticket> result = (List<Ticket>) ticketRepository.findAll(builder);
        return TicketFactory.toDTOs(result);
    }

    public void delete(Integer id) {
        log.debug("Request to delete Ticket: {}", id);
        ticketRepository.deleteById(id);
    }
}