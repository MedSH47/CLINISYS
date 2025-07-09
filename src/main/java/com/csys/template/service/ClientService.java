package com.csys.template.service;

import com.csys.template.domain.Client;
import com.csys.template.dtoRequest.ClientRequestDTO;
import com.csys.template.dtoResponse.ClientResponseDTO;
import com.csys.template.factory.ClientFactory;
import com.csys.template.repository.ClientRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientService {
    private final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final GeocodingService geocodingService;

    public ClientService(ClientRepository clientRepository, GeocodingService geocodingService) {
        this.clientRepository = clientRepository;
        this.geocodingService = geocodingService;
    }

    public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
        log.debug("Request to save Client : {}", clientRequestDTO);
        Client client = ClientFactory.toEntity(clientRequestDTO);

        if (client.getCountryCode() != null && client.getRegionName() != null) {
            try {
                Map<String, Double> coords = geocodingService.geocodeRegionAndCountry(
                        client.getCountryCode(),
                        client.getRegionName(),
                        client.getAdress()
                        );
                if (coords != null) {
                    client.setLatitude(coords.get("latitude"));
                    client.setLongitude(coords.get("longitude"));
                }
            } catch (Exception e) {
                log.error("Geocoding failed for new client {}: {}", client.getNomComplet(), e.getMessage());
            }
        }

        client = clientRepository.save(client);
        return ClientFactory.toResponseDTO(client);
    }

    public ClientResponseDTO update(Integer clientId, ClientRequestDTO clientRequestDTO) {
        log.debug("Request to update Client : {}", clientId);
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("client.NotFound"));

        String oldRegion = existingClient.getRegionName();
        String oldCountry = existingClient.getCountryCode();

        existingClient.setNomComplet(clientRequestDTO.getNomComplet());
        existingClient.setAdress(clientRequestDTO.getAdress());
        existingClient.setEmail(clientRequestDTO.getEmail());
        existingClient.setRegionName(clientRequestDTO.getRegionName());
        existingClient.setCountryCode(clientRequestDTO.getCountryCode());
        existingClient.setActif(clientRequestDTO.getActif());

      

        if (clientRequestDTO.getRegionName()!=null && clientRequestDTO.getCountryCode()!=null) {
            try {
                Map<String, Double> coords = geocodingService.geocodeRegionAndCountry(
                        existingClient.getCountryCode(),
                        existingClient.getRegionName(),
                        existingClient.getAdress());
                if (coords != null) {
                    existingClient.setLatitude(coords.get("latitude"));
                    existingClient.setLongitude(coords.get("longitude"));
                }
            } catch (Exception e) {
                log.error("Geocoding failed for updated client {}: {}", existingClient.getId(), e.getMessage());
            }
        }

        Client saved = clientRepository.save(existingClient);
        return ClientFactory.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findOne(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        return ClientFactory.toResponseDTO(client);
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAll() {
        return ClientFactory.toResponseDTOs(clientRepository.findAll());
    }

    public void delete(Integer id) {
        log.debug("Request to delete Client: {}", id);
        clientRepository.deleteById(id);
    }

    public List<String> getAllNames() {
        return clientRepository.findAll().stream()
                .map(Client::getNomComplet)
                .collect(Collectors.toList());
    }

    /**
     * NOUVELLE MÉTHODE : Calcule le nombre de nouveaux clients créés par heure.
     * 
     * @return Une liste de maps, chaque map représentant une heure avec les
     *         statistiques associées.
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getHourlyNewClientStats() {
        log.debug("Request to get hourly new client statistics");

        // Regroupe les clients par leur heure de création
        Map<Integer, Long> newClientsByHour = clientRepository.findAll().stream()
                .filter(c -> c.getDateCreation() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getDateCreation().getHour(),
                        Collectors.counting()));

        List<Map<String, Object>> hourlyData = new ArrayList<>();
        long totalClients = clientRepository.count();

        // Construit la liste de résultats pour chaque heure de la journée (0 à 23)
        for (int i = 0; i < 24; i++) {
            Map<String, Object> hourStats = new HashMap<>();
            hourStats.put("hour", String.format("%02d:00", i)); // Format "HH:00"
            hourStats.put("newClients", newClientsByHour.getOrDefault(i, 0L));
            hourStats.put("totalClients", totalClients); // Le total est le même pour chaque heure
            hourlyData.add(hourStats);
        }

        return hourlyData;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getClientMapStats(String mapType) {
        log.debug("Request to get client map statistics for type: {}", mapType);
        if ("world".equalsIgnoreCase(mapType)) {
            return clientRepository.getStatsByCountry();
        } else if ("tunisia".equalsIgnoreCase(mapType)) {
            return clientRepository.getStatsByRegionNameForCountry("TN");
        }
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> findAllClientLocations() {
        log.debug("Request to get all client locations for map markers");
        return clientRepository.findAll().stream()
                .filter(client -> client.getLatitude() != null && client.getLongitude() != null)
                .map(client -> {
                    Map<String, Object> locationData = new HashMap<>();
                    locationData.put("id", client.getId());
                    locationData.put("nomComplet", client.getNomComplet());
                    locationData.put("lat", client.getLatitude());
                    locationData.put("lng", client.getLongitude());
                    locationData.put("status", client.getActif());
                    return locationData;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> searchByTerm(String term) {
        if (term == null || term.isBlank() || term.length() < 2) {
            return Collections.emptyList();
        }
        List<Client> results = clientRepository.searchByTerm(term);
        return ClientFactory.toResponseDTOs(results);
    }
}