package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDTO;
import com.csys.template.util.Helper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientFactory {

    public static ClientDTO toDTO(Client client) {
        if (client == null) return null;
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNomComplet(client.getNomComplet());
        dto.setAdress(client.getAdress());
        dto.setEmail(client.getEmail());
        dto.setRegion(client.getRegion());
        dto.setDateCreation(client.getDateCreation());
        dto.setUserCreation(client.getUserCreation());
        dto.setActif(client.getActif());
        dto.setTicketList(TicketFactory.toDTOsLight(client.getTicketList()));
       
        return dto;
    }

    public static ClientDTO toDTOLight(Client client) {
        if (client == null) return null;
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNomComplet(client.getNomComplet());
        dto.setEmail(client.getEmail());
        return dto;
    }

    public static Client toEntity(ClientDTO dto) {
        if (dto == null) return null;
        Client entity = new Client();
        if(dto.getDateCreation() == null) {
            entity.setDateCreation(LocalDateTime.now());
        }
        entity.setUserCreation(Helper.getUserAuthenticated());
        entity.setId(dto.getId());
        entity.setNomComplet(dto.getNomComplet());
        entity.setAdress(dto.getAdress());
        entity.setEmail(dto.getEmail());
        entity.setRegion(dto.getRegion());
        entity.setActif(dto.getActif());
        return entity;
    }

    public static List<ClientDTO> toDTOs(Collection<Client> clients) {
        if (clients == null) return Collections.emptyList();
        return clients.stream().map(ClientFactory::toDTO).collect(Collectors.toList());
    }

    public static List<Client> toEntities(Collection<ClientDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(ClientFactory::toEntity).collect(Collectors.toList());
    }
}