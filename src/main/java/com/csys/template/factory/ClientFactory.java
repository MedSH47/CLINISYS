package com.csys.template.factory;

import com.csys.template.domain.Client;
import com.csys.template.dtoRequest.ClientRequestDTO;
import com.csys.template.dtoResponse.ClientResponseDTO;
import com.csys.template.util.Helper;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientFactory {

    public static ClientResponseDTO toResponseDTO(Client client) {
        if (client == null) return null;
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setNomComplet(client.getNomComplet());
        dto.setAdress(client.getAdress());
        dto.setEmail(client.getEmail());
        dto.setCountryCode(client.getCountryCode());
        dto.setRegionName(client.getRegionName());
        dto.setDateCreation(client.getDateCreation());
        dto.setUserCreation(client.getUserCreation());
        dto.setActif(client.getActif());
        
        // MAPPING ADDED for the new fields
        dto.setLatitude(client.getLatitude());
        dto.setLongitude(client.getLongitude());
        
        dto.setTicketList(TicketFactory.toDTOsLight(client.getTicketList()));
        return dto;
    }
    
    public static ClientResponseDTO toDTOLight(Client client) {
        if (client == null) return null;
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setNomComplet(client.getNomComplet());
        dto.setEmail(client.getEmail());
        dto.setCountryCode(client.getCountryCode());
        dto.setRegionName(client.getRegionName());
        dto.setActif(client.getActif());
        dto.setDateCreation(client.getDateCreation());
        dto.setUserCreation(client.getUserCreation());
        // Also add the new fields to the light DTO
        dto.setLatitude(client.getLatitude());
        dto.setLongitude(client.getLongitude());
        return dto;
    }

    public static Client toEntity(ClientRequestDTO dto) {
        if (dto == null) return null;
        Client entity = new Client();
        entity.setNomComplet(dto.getNomComplet());
        entity.setAdress(dto.getAdress());
        entity.setEmail(dto.getEmail());
        entity.setCountryCode(dto.getCountryCode());
        entity.setRegionName(dto.getRegionName());
        entity.setActif(dto.getActif());
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated());
        // Note: latitude and longitude are not set here, 
        // they will be set in the service after geocoding.
        return entity;
    }

    public static List<ClientResponseDTO> toResponseDTOs(Collection<Client> clients) {
        if (clients == null) return Collections.emptyList();
        return clients.stream().map(ClientFactory::toResponseDTO).collect(Collectors.toList());
    }
}