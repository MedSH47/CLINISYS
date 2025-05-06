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
    clientDTO.setNumClient(client.getNumClient());
    clientDTO.setAdress(client.getAdress());
    clientDTO.setTelephone(client.getTelephone());
    clientDTO.setNom(client.getNom());
    clientDTO.setPrenom(client.getPrenom());
    clientDTO.setCreationDate(client.getCreationDate());
    clientDTO.setCreationUser(client.getCreationUser());
    clientDTO.setTicketList(client.getTicketList());
    return clientDTO;
  }

  @SuppressWarnings("unchecked")
  public static Client clientDTOToClient(ClientDTO clientDTO) {
    Client client=new Client();
    client.setId(clientDTO.getId());
    client.setNumClient(clientDTO.getNumClient());
    client.setAdress(clientDTO.getAdress());
    client.setTelephone(clientDTO.getTelephone());
    client.setNom(clientDTO.getNom());
    client.setPrenom(clientDTO.getPrenom());
    client.setCreationDate(clientDTO.getCreationDate());
    client.setCreationUser(clientDTO.getCreationUser());
    client.setTicketList(clientDTO.getTicketList());
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

