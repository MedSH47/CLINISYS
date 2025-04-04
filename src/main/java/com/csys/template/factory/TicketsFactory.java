package com.csys.template.factory;

import java.util.ArrayList;
import java.util.List;

import com.csys.template.domain.Ticket;
import com.csys.template.dto.TicketDto;

public class TicketsFactory {
    public static Ticket ticketDtoToTicket(TicketDto ticketsDto) {
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(ticketsDto.getTicketNumber());
        ticket.setDate(ticketsDto.getDate());
        return ticket;

    }
    public static TicketDto ticketToTicketDto(Ticket ticket) {
        if (ticket == null) {
            return null; 
        }
        TicketDto ticketsDto = new TicketDto();
        ticketsDto.setTicketNumber(ticket.getTicketNumber());
        ticketsDto.setDate(ticket.getDate());
        return ticketsDto;
    }
    public static List<TicketDto> ticketsDtoTicketsDtos(List<Ticket> tickets){
        List<TicketDto> ticketsDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            TicketDto ticketDto = ticketToTicketDto(ticket);
            ticketsDtos.add(ticketDto);
        }
        return ticketsDtos;

    }
}
