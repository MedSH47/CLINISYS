package com.csys.template.dtoRequest;

public class ClientLocationRequestDTO {
    private Integer clientId;
    private Double latitude;
    private Double longitude;

    public ClientLocationRequestDTO(Integer clientId, Double latitude, Double longitude) {
        this.clientId = clientId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ClientLocationRequestDTO() {
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
