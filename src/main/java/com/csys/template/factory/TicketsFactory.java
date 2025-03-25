package com.csys.template.factory;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketsDto;

public class TicketsFactory {

    public static Ticket ticketsDtoToTickets(TicketsDto ticketsDto) {
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(ticketsDto.getTicketNumber());
        ticket.setDate(ticketsDto.getDate());
        return ticket;

    }
    public static TicketsDto ticketsToTicketsDto(Ticket ticket) {
        if (ticket == null) {
            return null; 
        }
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setTicketNumber(ticket.getTicketNumber());
        ticketsDto.setDate(ticket.getDate());
        return ticketsDto;
    }
    
}
