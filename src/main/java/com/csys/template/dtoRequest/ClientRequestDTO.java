package com.csys.template.dtoRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientRequestDTO {

    @NotNull(message = "Le nom du client ne peut pas être nul")
    @Size(min = 1, max = 100, message = "Le nom du client doit contenir entre 1 et 100 caractères")
    private String nomComplet;

    @Size(max = 200, message = "L'adresse ne peut pas dépasser 200 caractères")
    private String adress;

    @Email(message = "Veuillez fournir une adresse email valide")
    @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
    private String email;

    // REMPLACÉ : private String region;
    // PAR :
    @Size(max = 10, message = "Le code pays ne peut pas dépasser 10 caractères")
    private String countryCode; // Ex: "TN", "FR", "US"

    @Size(max = 100, message = "Le nom de la région ne peut pas dépasser 100 caractères")
    private String regionName; // Ex: "Sfax", "Île-de-France", "California"

    @NotNull(message = "Le statut Actif ne peut pas être nul")
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