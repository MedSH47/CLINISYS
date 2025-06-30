package com.csys.template.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional; // NOUVEAU
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csys.template.domain.Client;
import com.csys.template.domain.ClientLocation; // NOUVEAU
import com.csys.template.dtoRequest.ClientRequestDTO;
import com.csys.template.dtoResponse.ClientLocationDTO; // NOUVEAU
import com.csys.template.dtoResponse.ClientResponseDTO;
import com.csys.template.factory.ClientFactory;
import com.csys.template.repository.ClientLocationRepository; // NOUVEAU
import com.csys.template.repository.ClientRepository;



@Service
@Transactional
public class ClientService {
    private final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final ClientLocationRepository clientLocationRepository; // NOUVEAU
    private final GeocodingService geocodingService; // NOUVEAU

    public ClientService(ClientRepository clientRepository, ClientLocationRepository clientLocationRepository, GeocodingService geocodingService) {
        this.clientRepository = clientRepository;
        this.clientLocationRepository = clientLocationRepository;
        this.geocodingService = geocodingService;
    }

public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
    log.debug("Request to save Client : {}", clientRequestDTO);

    Client client = ClientFactory.toEntity(clientRequestDTO);
    client = clientRepository.save(client);

    if (client.getCountryCode() != null && client.getRegionName() != null) {
        try {
            Map<String, Double> coords = geocodingService.geocodeRegionAndCountry(
                client.getCountryCode(),
                client.getRegionName()
            );
            if (coords != null) {
                ClientLocation clientLocation = new ClientLocation(
                    client.getId(),
                    coords.get("latitude"),
                    coords.get("longitude")
                );
                clientLocationRepository.save(clientLocation);
            }
        } catch (Exception e) {
            log.error("Erreur géocodage pour client {}: {}", client.getId(), e.getMessage());
        }
    }

    return ClientFactory.toResponseDTO(client);
}

public ClientResponseDTO update(Integer clientId, ClientRequestDTO clientRequestDTO) {
    log.debug("Request to update Client : {}", clientId);

    Client existingClient = clientRepository.findById(clientId)
            .orElseThrow(() -> new IllegalArgumentException("client.NotFound"));

    String oldRegion = existingClient.getRegionName();
    String oldCountry = existingClient.getCountryCode();

    // Update fields
    existingClient.setNomComplet(clientRequestDTO.getNomComplet());
    existingClient.setAdress(clientRequestDTO.getAdress());
    existingClient.setEmail(clientRequestDTO.getEmail());
    existingClient.setRegionName(clientRequestDTO.getRegionName());
    existingClient.setCountryCode(clientRequestDTO.getCountryCode());
    existingClient.setActif(clientRequestDTO.getActif());

    Client saved = clientRepository.save(existingClient);

    boolean regionChanged = clientRequestDTO.getRegionName() != null &&
                            !clientRequestDTO.getRegionName().equals(oldRegion);
    boolean countryChanged = clientRequestDTO.getCountryCode() != null &&
                             !clientRequestDTO.getCountryCode().equals(oldCountry);

    boolean locationMissing = !clientLocationRepository.existsById(saved.getId());

    if ((regionChanged || countryChanged) || locationMissing) {
        try {
            Map<String, Double> coords = geocodingService.geocodeRegionAndCountry(
                saved.getCountryCode(),
                saved.getRegionName()
            );
            if (coords != null) {
                ClientLocation clientLocation = clientLocationRepository.findById(saved.getId())
                        .orElse(new ClientLocation(saved.getId(), null, null));
                clientLocation.setLatitude(coords.get("latitude"));
                clientLocation.setLongitude(coords.get("longitude"));
                clientLocationRepository.save(clientLocation);
            }
        } catch (Exception e) {
            log.error("Erreur géocodage pour mise à jour client {}: {}", saved.getId(), e.getMessage());
        }
    }

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
        // NOUVEAU: Supprimer aussi la localisation associée si elle existe
        clientLocationRepository.deleteById(id); // clientId est la clé primaire dans ClientLocation
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
            List<Map<String, Object>> statsByCountryCode = clientRepository.getStatsByCountry();
            return statsByCountryCode;

        } else if ("tunisia".equalsIgnoreCase(mapType)) {
            return clientRepository.getStatsByRegionNameForCountry("TN");
        }

        return new ArrayList<>();
    }

    /**
     * NOUVEAU: Récupère la liste de tous les clients avec leurs coordonnées depuis la table séparée.
     * C'est cette méthode qui sera appelée par le frontend pour afficher les marqueurs.
     * @return Une liste de ClientLocationDTOs.
     */
    @Transactional(readOnly = true)
    public List<ClientLocationDTO> findAllClientLocations() {
        log.debug("Request to get all client locations for map markers from separate table");
        
        // Récupère tous les clients et leurs localisations (s'ils existent)
        // Note: Cela pourrait être optimisé avec une jointure dans un Repository personnalisé
        // ou en récupérant les ClientLocation séparément puis en les mappant.
        // Pour l'instant, on fait une boucle sur tous les clients et on cherche leur localisation.
        List<Client> clients = clientRepository.findAll();
        List<ClientLocation> clientLocations = clientLocationRepository.findAll(); // Récupère toutes les localisations

        Map<Integer, ClientLocation> locationsMap = clientLocations.stream()
            .collect(Collectors.toMap(ClientLocation::getClientId, location -> location));

        return clients.stream()
                .map(client -> {
                    ClientLocation loc = locationsMap.get(client.getId());
                    if (loc != null && loc.getLatitude() != null && loc.getLongitude() != null) {
                        return new ClientLocationDTO(
                            client.getId(),
                            client.getNomComplet(),
                            loc.getLatitude(),
                            loc.getLongitude(),
                            client.getActif()
                        );
                    }
                    return null; // Si pas de localisation trouvée ou coordonnées manquantes
                })
                .filter(java.util.Objects::nonNull) // Filtre les clients sans localisation
                .collect(Collectors.toList());
    }
}