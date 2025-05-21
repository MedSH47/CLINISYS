package com.csys.template.factory;

import com.csys.template.domain.Fichiersjointes;
import com.csys.template.domain.Ticketfichier;
import com.csys.template.dto.FichiersjointesDTO;
import com.csys.template.dto.TicketfichierDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FichiersjointesFactory {
  public static FichiersjointesDTO fichiersjointesToFichiersjointesDTO(Fichiersjointes fichiersjointes) {
    FichiersjointesDTO fichiersjointesDTO=new FichiersjointesDTO();
    fichiersjointesDTO.setId(fichiersjointes.getId());
    fichiersjointesDTO.setNumFichier(fichiersjointes.getNumFichier());
    fichiersjointesDTO.setDesignation(fichiersjointes.getDesignation());
    Collection<TicketfichierDTO> ticketfichierCollectionDtos = new ArrayList<>();
    fichiersjointes.getTicketfichierCollection().forEach(x -> {
      TicketfichierDTO ticketfichierDto = new TicketfichierDTO();
      ticketfichierDto = TicketfichierFactory.ticketfichierToTicketfichierDTO(x);
      ticketfichierCollectionDtos.add(ticketfichierDto);
    } );
    if(fichiersjointesDTO.getTicketfichierCollection() !=null) {
      fichiersjointesDTO.getTicketfichierCollection().clear();
      fichiersjointesDTO.getTicketfichierCollection().addAll(ticketfichierCollectionDtos);
    }
    else {
      fichiersjointesDTO.setTicketfichierCollection(ticketfichierCollectionDtos);
    }
    return fichiersjointesDTO;
  }

  public static Fichiersjointes fichiersjointesDTOToFichiersjointes(FichiersjointesDTO fichiersjointesDTO) {
    Fichiersjointes fichiersjointes=new Fichiersjointes();
    fichiersjointes.setId(fichiersjointesDTO.getId());
    fichiersjointes.setNumFichier(fichiersjointesDTO.getNumFichier());
    fichiersjointes.setDesignation(fichiersjointesDTO.getDesignation());
    Collection<Ticketfichier> ticketfichierCollections = new ArrayList<>();
    fichiersjointesDTO.getTicketfichierCollection().forEach(x -> {
      Ticketfichier ticketfichier = new Ticketfichier();
      ticketfichier = TicketfichierFactory.ticketfichierDTOToTicketfichier(x);
      ticketfichierCollections.add(ticketfichier);
    } );
    if(fichiersjointes.getTicketfichierCollection() !=null) {
      fichiersjointes.getTicketfichierCollection().clear();
      fichiersjointes.getTicketfichierCollection().addAll(ticketfichierCollections);
    }
    else {
      fichiersjointes.setTicketfichierCollection(ticketfichierCollections);
    }
    return fichiersjointes;
  }

  public static Collection<FichiersjointesDTO> fichiersjointesToFichiersjointesDTOs(Collection<Fichiersjointes> fichiersjointess) {
    List<FichiersjointesDTO> fichiersjointessDTO=new ArrayList<>();
    fichiersjointess.forEach(x -> {
      fichiersjointessDTO.add(fichiersjointesToFichiersjointesDTO(x));
    } );
    return fichiersjointessDTO;
  }

  public static FichiersjointesDTO lazyfichiersjointesToFichiersjointesDTO(Fichiersjointes fichiersjointes) {
    FichiersjointesDTO fichiersjointesDTO=new FichiersjointesDTO();
    fichiersjointesDTO.setId(fichiersjointes.getId());
    fichiersjointesDTO.setNumFichier(fichiersjointes.getNumFichier());
    fichiersjointesDTO.setDesignation(fichiersjointes.getDesignation());
    return fichiersjointesDTO;
  }

  public static Collection<FichiersjointesDTO> lazyfichiersjointesToFichiersjointesDTOs(Collection<Fichiersjointes> fichiersjointess) {
    List<FichiersjointesDTO> fichiersjointessDTO=new ArrayList<>();
    fichiersjointess.forEach(x -> {
      fichiersjointessDTO.add(lazyfichiersjointesToFichiersjointesDTO(x));
    } );
    return fichiersjointessDTO;
  }
}

