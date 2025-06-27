package com.csys.template.dtoResponse.geo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private String code; // Code de la région (ex: TN-11 pour Tunis)
    private String name; // Nom de la région (ex: Tunis)

    // Constructeurs
    public RegionDTO() {}
    public RegionDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters et Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
