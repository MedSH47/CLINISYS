package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDTO;
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
    clientDTO.setDateCreation(client.getDateCreation());
    clientDTO.setUserCreation(client.getUserCreation());
    clientDTO.setActif(client.getActif());
    clientDTO.setTicketSet(client.getTicketSet());
    return clientDTO;
  }

  public static Client clientDTOToClient(ClientDTO clientDTO) {
    Client client=new Client();
    client.setId(clientDTO.getId());
    client.setNomComplet(clientDTO.getNomComplet());
    client.setAdress(clientDTO.getAdress());
    client.setEmail(clientDTO.getEmail());
    client.setRegion(clientDTO.getRegion());
    client.setDateCreation(clientDTO.getDateCreation());
    client.setUserCreation(clientDTO.getUserCreation());
    client.setActif(clientDTO.getActif());
    client.setTicketSet(clientDTO.getTicketSet());
    return client;
  }

  public static Collection<ClientDTO> clientToClientDTOs(Collection<Client> clients) {
    List<ClientDTO> clientsDTO=new ArrayList<>();
    clients.forEach(x -> {
      clientsDTO.add(clientToClientDTO(x));
    } );
    return clientsDTO;
  }
}

