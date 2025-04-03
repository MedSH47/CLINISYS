package com.csys.template.service;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketDto;
import com.csys.template.factory.TicketsFactory;
import com.csys.template.repository.TicketRepository;
import com.csys.template.util.RestPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket addTicket(Ticket entity) {
        return ticketRepository.save(entity);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(Ticket entity) {
        return ticketRepository.save(entity);
    }

    public TicketDto findOne(Integer id) {
        Ticket t = ticketRepository.findOneById(id);
        return TicketsFactory.ticketToTicketDto(t);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }
}
