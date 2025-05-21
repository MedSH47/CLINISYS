package com.csys.template.factory;

import com.csys.template.domain.Ticketfichier;
import com.csys.template.dto.TicketfichierDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TicketfichierFactory {
  public static TicketfichierDTO ticketfichierToTicketfichierDTO(Ticketfichier ticketfichier) {
    TicketfichierDTO ticketfichierDTO=new TicketfichierDTO();
    ticketfichierDTO.setId(ticketfichier.getId());
    ticketfichierDTO.setIdFichier(ticketfichier.getIdFichier());
    ticketfichierDTO.setIdTicket(ticketfichier.getIdTicket());
    return ticketfichierDTO;
  }

  public static Ticketfichier ticketfichierDTOToTicketfichier(TicketfichierDTO ticketfichierDTO) {
    Ticketfichier ticketfichier=new Ticketfichier();
    ticketfichier.setId(ticketfichierDTO.getId());
    ticketfichier.setIdFichier(ticketfichierDTO.getIdFichier());
    ticketfichier.setIdTicket(ticketfichierDTO.getIdTicket());
    return ticketfichier;
  }

  public static Collection<TicketfichierDTO> ticketfichierToTicketfichierDTOs(Collection<Ticketfichier> ticketfichiers) {
    List<TicketfichierDTO> ticketfichiersDTO=new ArrayList<>();
    ticketfichiers.forEach(x -> {
      ticketfichiersDTO.add(ticketfichierToTicketfichierDTO(x));
    } );
    return ticketfichiersDTO;
  }
}

