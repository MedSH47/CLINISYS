package com.csys.template.web.rest.ressource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.dtoResponse.geo.CountryDTO;
import com.csys.template.dtoResponse.geo.RegionDTO;
import com.csys.template.service.GeoDataService;

@RestController
@RequestMapping("/api/geo")
public class GeoDataResource {
    private static final Logger log = LoggerFactory.getLogger(GeoDataResource.class);
    private final GeoDataService geoDataService;

    public GeoDataResource(GeoDataService geoDataService) {
        this.geoDataService = geoDataService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        log.debug("REST request to get all countries");
        return ResponseEntity.ok(geoDataService.getAllCountries());
    }

    @GetMapping("/countries/{countryCode}/regions")
    public ResponseEntity<List<RegionDTO>> getRegionsByCountryCode(@PathVariable String countryCode) {
        log.debug("REST request to get regions for country code: {}", countryCode);
        return ResponseEntity.ok(geoDataService.getRegionsByCountryCode(countryCode));
    }
}