package com.csys.template.dtoResponse.geo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDTO {
    private String code; // Code ISO du pays (ex: TN pour Tunisie, US pour États-Unis)
    private String name; // Nom du pays (ex: Tunisie, United States)
    private List<RegionDTO> regions; // Liste des régions/gouvernorats de ce pays

    // Constructeurs
    public CountryDTO() {
        this.regions = new ArrayList<>();
    }
    public CountryDTO(String code, String name, List<RegionDTO> regions) {
        this.code = code;
        this.name = name;
        this.regions = regions;
    }

    // Getters et Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<RegionDTO> getRegions() { return regions; }
    public void setRegions(List<RegionDTO> regions) { this.regions = regions; }
}