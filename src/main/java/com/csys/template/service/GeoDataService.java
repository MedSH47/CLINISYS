package com.csys.template.service;

import com.csys.template.dtoResponse.geo.CountryDTO;
import com.csys.template.dtoResponse.geo.RegionDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GeoDataService {

    private static final Logger log = LoggerFactory.getLogger(GeoDataService.class);

    private List<CountryDTO> countries;
    private final ObjectMapper objectMapper;

    public GeoDataService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        String filePath = "static/geo_data.json"; // Path inside src/main/resources

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            countries = objectMapper.readValue(inputStream, new TypeReference<List<CountryDTO>>() {});
            log.info("Chargement réussi de {} pays depuis la ressource du classpath.", countries.size());

        } catch (IOException e) {
            log.error("Erreur de lecture du fichier geo_data.json depuis le classpath : {}", filePath, e);
            countries = Collections.emptyList();
        } catch (Exception e) {
            log.error("Erreur critique inattendue lors du chargement de geo_data.json.", e);
            countries = Collections.emptyList();
        }
    }

    public List<CountryDTO> getAllCountries() {
        return Collections.unmodifiableList(countries);
    }

    public List<RegionDTO> getRegionsByCountryCode(String countryCode) {
        return countries.stream()
                .filter(country -> country.getCode().equalsIgnoreCase(countryCode))
                .findFirst()
                .map(CountryDTO::getRegions)
                .orElse(Collections.emptyList());
    }

    public Optional<RegionDTO> getRegionByName(String countryCode, String regionName) {
        return getRegionsByCountryCode(countryCode).stream()
                .filter(region -> region.getName().equalsIgnoreCase(regionName))
                .findFirst();
    }
}
