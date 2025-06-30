package com.csys.template.dtoResponse;


public class ClientLocationDTO {
    private Integer id;
    private String nomComplet;
    private Double lat;
    private Double lng;
    private Boolean status; // Pour indiquer si le client est actif ou non

    public ClientLocationDTO() {}

    public ClientLocationDTO(Integer id, String nomComplet, Double lat, Double lng, Boolean status) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
