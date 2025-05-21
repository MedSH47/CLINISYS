package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.domain.Ticket;
import com.csys.template.dto.ClientDTO;
import com.csys.template.dto.TicketDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientFactory {
  public static ClientDTO clientToClientDTO(Client client) {
    ClientDTO clientDTO=new ClientDTO();
    clientDTO.setId(client.getId());
    clientDTO.setNomComplet(client.getNomComplet());
    clientDTO.setAdress(client.getAdress());
    clientDTO.setEmail(client.getEmail());
    clientDTO.setRegion(client.getRegion());
    clientDTO.setActif(client.getActif());
    Collection<TicketDTO> ticketCollectionDtos = new ArrayList<>();
    client.getTicketCollection().forEach(x -> {
      TicketDTO ticketDto = new TicketDTO();
      ticketDto = TicketFactory.ticketToTicketDTO(x);
      ticketCollectionDtos.add(ticketDto);
    } );
    if(clientDTO.getTicketCollection() !=null) {
      clientDTO.getTicketCollection().clear();
      clientDTO.getTicketCollection().addAll(ticketCollectionDtos);
    }
    else {
      clientDTO.setTicketCollection(ticketCollectionDtos);
    }
    return clientDTO;
  }

  public static Client clientDTOToClient(ClientDTO clientDTO) {
    Client client=new Client();
    client.setId(clientDTO.getId());
    client.setNomComplet(clientDTO.getNomComplet());
    client.setAdress(clientDTO.getAdress());
    client.setEmail(clientDTO.getEmail());
    client.setRegion(clientDTO.getRegion());
    client.setActif(clientDTO.getActif());
    Collection<Ticket> ticketCollections = new ArrayList<>();
    clientDTO.getTicketCollection().forEach(x -> {
      Ticket ticket = new Ticket();
      ticket = TicketFactory.ticketDTOToTicket(x);
      ticketCollections.add(ticket);
    } );
    if(client.getTicketCollection() !=null) {
      client.getTicketCollection().clear();
      client.getTicketCollection().addAll(ticketCollections);
    }
    else {
      client.setTicketCollection(ticketCollections);
    }
    return client;
  }

  public static Collection<ClientDTO> clientToClientDTOs(Collection<Client> clients) {
    List<ClientDTO> clientsDTO=new ArrayList<>();
    clients.forEach(x -> {
      clientsDTO.add(clientToClientDTO(x));
    } );
    return clientsDTO;
  }

  public static ClientDTO lazyclientToClientDTO(Client client) {
    ClientDTO clientDTO=new ClientDTO();
    clientDTO.setId(client.getId());
    clientDTO.setNomComplet(client.getNomComplet());
    clientDTO.setAdress(client.getAdress());
    clientDTO.setEmail(client.getEmail());
    clientDTO.setRegion(client.getRegion());
    clientDTO.setActif(client.getActif());
    return clientDTO;
  }

  public static Collection<ClientDTO> lazyclientToClientDTOs(Collection<Client> clients) {
    List<ClientDTO> clientsDTO=new ArrayList<>();
    clients.forEach(x -> {
      clientsDTO.add(lazyclientToClientDTO(x));
    } );
    return clientsDTO;
  }
}

