package com.csys.template.dtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ClientResponseDTO {

    private Integer id;
    private String nomComplet;
    private String adress;
    private String email;
    
    private String countryCode;
    private String regionName;

    // FIELDS ADDED
    private Double latitude;
    private Double longitude;
    
    private LocalDateTime dateCreation;
    private String userCreation;
    private Boolean actif;
    
    private List<TicketResponseDTO> ticketList; 

    // --- Getters and Setters ---
    
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
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
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
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    public String getUserCreation() {
        return userCreation;
    }
    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }
    public Boolean getActif() {
        return actif;
    }
    public void setActif(Boolean actif) {
        this.actif = actif;
    }
    public List<TicketResponseDTO> getTicketList() {
        return ticketList;
    }
    public void setTicketList(List<TicketResponseDTO> ticketList) {
        this.ticketList = ticketList;
    }
}