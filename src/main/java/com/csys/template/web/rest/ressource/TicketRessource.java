package com.csys.template.web.rest.ressource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.domain.Ticket;
import com.csys.template.repository.TicketRepository;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/Ticket")
public class TicketRessource {

    
    
    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket entity) throws URISyntaxException {
        Ticket ticket = ticketRepository.save(entity);
        return ResponseEntity.created(new URI("/api/Ticket/"+ticket.getId())).body(ticket);
    }
    @GetMapping
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }
    @PutMapping
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket entity) throws URISyntaxException {
        Ticket ticket = ticketRepository.save(entity);
        return ResponseEntity.ok(ticket);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id){
        ticketRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
