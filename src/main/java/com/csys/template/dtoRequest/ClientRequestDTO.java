package com.csys.template.dtoRequest;

import javax.validation.constraints.Email;


public class ClientRequestDTO {

    private String nomComplet;

    private String adress;

    @Email(message = "Veuillez fournir une adresse email valide")
    private String email;

    // REMPLACÉ : private String region;
    // PAR :
    private String countryCode; // Ex: "TN", "FR", "US"

    private String regionName; // Ex: "Sfax", "Île-de-France", "California"

    private Boolean actif;

    // Getters and Setters
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

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}