package com.csys.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csys.template.domain.Client;
import com.csys.template.dtoRequest.ClientRequestDTO;
import com.csys.template.dtoResponse.ClientResponseDTO;
import com.csys.template.factory.ClientFactory;
import com.csys.template.repository.ClientRepository;

@Service
@Transactional
public class ClientService {
    private final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
        log.debug("Request to save Client : {}", clientRequestDTO);
        Client client = ClientFactory.toEntity(clientRequestDTO);
        client = clientRepository.save(client);
        return ClientFactory.toResponseDTO(client);
    }

    public ClientResponseDTO update(Integer clientId, ClientRequestDTO clientRequestDTO) {
        log.debug("Request to update Client : {}", clientId);
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("client.NotFound"));

        existingClient.setNomComplet(clientRequestDTO.getNomComplet());
        existingClient.setAdress(clientRequestDTO.getAdress());
        existingClient.setEmail(clientRequestDTO.getEmail());
        existingClient.setRegionName(clientRequestDTO.getRegionName());
        existingClient.setActif(clientRequestDTO.getActif());

        Client saved = clientRepository.save(existingClient);
        return ClientFactory.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findOne(Integer id) {
        log.debug("Request to get Client : {}", id);
        Client client = clientRepository.findById(id).orElse(null);
        return ClientFactory.toResponseDTO(client);
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAll() {
        log.debug("Request to get All Clients");
        List<Client> result = clientRepository.findAll();
        return ClientFactory.toResponseDTOs(result);
    }

    public void delete(Integer id) {
        log.debug("Request to delete Client: {}", id);
        clientRepository.deleteById(id);
    }
    public List<String> getAllNames(){
        log.debug("Request to get all names of clients: {}");
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                  .map(Client::getNomComplet)
                  .collect(Collectors.toList());
    }
    // Exemple dans ClientService.java
// N'oubliez pas les imports et annotations
    public List<Map<String, Object>> getHourlyNewClientStats() {
        List<Client> clients = clientRepository.findAll(); // Ou filter par date récente
        Map<Integer, Long> newClientsByHour = clients.stream()
            .filter(c -> c.getDateCreation() != null)
            .collect(Collectors.groupingBy(
                c -> c.getDateCreation().getHour(),
                Collectors.counting()
            ));

        List<Map<String, Object>> hourlyData = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            int hour = i;
            long count = newClientsByHour.getOrDefault(hour, 0L);
            hourlyData.add(Map.of(
                "hour", String.format("%02d:00", hour),
                "newClients", count,
                "totalClients", clientRepository.count() // Simpler, can be optimized
            ));
        }
        return hourlyData;
    }
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getClientMapStats(String mapType) {
        log.debug("Request to get client map statistics for type: {}", mapType);

        if ("world".equalsIgnoreCase(mapType)) {
            // Pour la carte du monde, nous devons faire correspondre le code du pays (TN, US) au nom du pays ("Tunisia", "United States")
            // que le fichier GeoJSON attend.
            List<Map<String, Object>> statsByCountryCode = clientRepository.getStatsByCountry();
            // Vous devrez peut-être transformer les codes (ex: "US") en noms complets ("United States")
            // si votre GeoJSON de la carte du monde utilise des noms complets.
            // Pour cet exemple, nous supposons que le frontend peut gérer la correspondance ou que le GeoJSON utilise des codes.
            // Idéalement, le GeoJSON utiliserait des codes ISO standard.
            return statsByCountryCode;

        } else if ("tunisia".equalsIgnoreCase(mapType)) {
            return clientRepository.getStatsByRegionNameForCountry("TN");
        }

        return new ArrayList<>();
    }
   
}