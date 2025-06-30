// src/main/java/com/csys/template/util/GeocodingService.java
package com.csys.template.service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper; // Pour parser la réponse JSON

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {

    private final Logger log = LoggerFactory.getLogger(GeocodingService.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // Pour une meilleure gestion des JSON

    // URL de l'API de géocodage (Exemple avec Nominatim d'OpenStreetMap)
    // N'oubliez pas de respecter les politiques d'utilisation de l'API que vous choisissez.
    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";

    public GeocodingService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Géocode une adresse en coordonnées latitude et longitude.
     * @param address L'adresse à géocoder.
     * @param countryCode Le code du pays (ex: "TN", "FR"), peut améliorer la précision.
     * @param regionName Le nom de la région/gouvernorat, peut améliorer la précision.
     * @return Une Map contenant "latitude" et "longitude", ou null si échec.
     */
    public Map<String, Double> geocodeAddress(String address, String countryCode, String regionName) {
        String query = address;
        if (regionName != null && !regionName.isEmpty()) {
            query += ", " + regionName;
        }
        if (countryCode != null && !countryCode.isEmpty()) {
            query += ", " + countryCode;
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(NOMINATIM_API_URL)
                .queryParam("q", query)
                .queryParam("format", "json")
                .queryParam("limit", 1); // Limite à un seul résultat pertinent

        try {
            String jsonResponse = restTemplate.getForObject(builder.toUriString(), String.class);
            List<Map<String, Object>> results = objectMapper.readValue(jsonResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            if (results != null && !results.isEmpty()) {
                Map<String, Object> firstResult = results.get(0);
                Double lat = Double.parseDouble(firstResult.get("lat").toString());
                Double lon = Double.parseDouble(firstResult.get("lon").toString());

                Map<String, Double> coords = new HashMap<>();
                coords.put("latitude", lat);
                coords.put("longitude", lon);
                return coords;
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'appel à l'API de géocodage pour l'adresse '{}': {}", query, e.getMessage());
            // Pour les erreurs de Rate Limiting ou autres, vous pourriez implémenter une logique de nouvelle tentative.
        }
        return null;
    }
}