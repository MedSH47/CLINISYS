package com.csys.template.factory;

import java.util.ArrayList;
import java.util.List;

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDto;

public class ClientFactory {

    public static Client clientDtoToClient(ClientDto clientDto) {
        Client client = new Client();
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setEmail(clientDto.getEmail());
        client.setTelephone(clientDto.getTelephone());
        client.setAdresse(clientDto.getAdresse());
        client.setVille(clientDto.getVille());
        return client;
    }

    public static ClientDto clientToClientDto(Client client) {
        if (client == null) {
            return null; 
        }
        ClientDto clientDto = new ClientDto();
        clientDto.setNom(client.getNom());
        clientDto.setPrenom(client.getPrenom());
        clientDto.setEmail(client.getEmail());
        clientDto.setTelephone(client.getTelephone());
        clientDto.setAdresse(client.getAdresse());
        clientDto.setVille(client.getVille());
        return clientDto;
    }

    public static List<ClientDto> clientsToClientsDtos(List<Client> clients) {
        List<ClientDto> clientsDtos = new ArrayList<>();
        for (Client client : clients) {
            ClientDto clientDto = clientToClientDto(client);
            clientsDtos.add(clientDto);
        }
        return clientsDtos;
    }
}