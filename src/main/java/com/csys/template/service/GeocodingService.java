package com.csys.template.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.csys.template.dtoProjection.GeocodingResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate;

    public GeocodingService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Double> geocodeRegionAndCountry(String country, String region,String speceficadress) {
        String url = "http://localhost:5001/geo";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("location", region + ", " + country+"specefic adress:"+speceficadress);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        
        // --- CHANGE IS HERE ---
        // Tell RestTemplate to map the response directly to our DTO
        ResponseEntity<GeocodingResponseDTO> response = restTemplate.postForEntity(url, request, GeocodingResponseDTO.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            GeocodingResponseDTO responseBody = response.getBody();
            
            // --- NEW, SAFER LOGIC ---
            if (responseBody.getCoordinates() != null) {
                Map<String, Double> coords = new HashMap<>();
                coords.put("latitude", responseBody.getCoordinates().getLatitude());
                coords.put("longitude", responseBody.getCoordinates().getLongitude());
                return coords;
            }
        }
        return null; // Or throw a custom exception
    }
}