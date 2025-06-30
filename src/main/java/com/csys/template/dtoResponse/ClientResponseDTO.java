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
    
    private LocalDateTime dateCreation;
    private String userCreation;
    private Boolean actif;
    
    private List<TicketResponseDTO> ticketList; 

    // Getters and Setters
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

    // AJOUTÉ : Getters et Setters pour les nouveaux champs
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

    // SUPPRIMÉ : Getters et Setters pour 'region'

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