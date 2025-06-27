package com.csys.template.service;

import com.csys.template.dtoResponse.geo.CountryDTO;
import com.csys.template.dtoResponse.geo.RegionDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Service
public class GeoDataService {
    private static final Logger log = LoggerFactory.getLogger(GeoDataService.class);
    private List<CountryDTO> countries;
    private final ObjectMapper objectMapper; // Spring Boot auto-configure ObjectMapper

    public GeoDataService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            // Chemin vers le fichier JSON dans src/main/resources/static
            ClassPathResource resource = new ClassPathResource("static/geo_data.json");
            try (InputStream inputStream = resource.getInputStream()) {
                countries = objectMapper.readValue(inputStream, new TypeReference<List<CountryDTO>>() {});
                log.info("Chargement de {} pays et de leurs régions à partir de geo_data.json", countries.size());
            }
        } catch (IOException e) {
            log.error("Erreur lors du chargement des données géographiques depuis geo_data.json", e);
            countries = Collections.emptyList(); // S'assurer que la liste n'est pas nulle
        }
    }

    public List<CountryDTO> getAllCountries() {
        return Collections.unmodifiableList(countries); // Retourne une liste non modifiable
    }

    public List<RegionDTO> getRegionsByCountryCode(String countryCode) {
        return countries.stream()
                .filter(country -> country.getCode().equalsIgnoreCase(countryCode))
                .findFirst()
                .map(CountryDTO::getRegions)
                .orElse(Collections.emptyList());
    }

    // Utile pour la carte si vous voulez valider que le nom de région du client existe dans nos données
    public Optional<RegionDTO> getRegionByName(String countryCode, String regionName) {
        return getRegionsByCountryCode(countryCode).stream()
                .filter(region -> region.getName().equalsIgnoreCase(regionName))
                .findFirst();
    }
}