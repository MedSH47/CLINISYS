package com.csys.template.dtoProjection;

// We only map the fields we need, but you could add all of them (city, country_name, etc.)
public class GeocodingResponseDTO {
    private CoordinatesDTO coordinates;

    // Getters and Setters
    public CoordinatesDTO getCoordinates() { return coordinates; }
    public void setCoordinates(CoordinatesDTO coordinates) { this.coordinates = coordinates; }
}