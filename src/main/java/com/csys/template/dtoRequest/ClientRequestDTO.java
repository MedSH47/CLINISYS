package com.csys.template.dtoRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientRequestDTO {

    @NotNull(message = "Client name cannot be null")
    @Size(min = 1, max = 100, message = "Client name must be between 1 and 100 characters")
    private String nomComplet;

    @Size(max = 200, message = "Address cannot exceed 200 characters")
    private String adress;

    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Size(max = 50, message = "Region cannot exceed 50 characters")
    private String region;

    @NotNull(message = "Actif status cannot be null")
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}