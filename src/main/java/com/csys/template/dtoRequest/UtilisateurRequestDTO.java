package com.csys.template.dtoRequest;

import com.csys.template.domain.enum_identifier.Role;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UtilisateurRequestDTO {

    @Size(min = 1, max = 50)
    private String nom;

    @Size(min = 1, max = 50)
    private String prenom;

    @Size(min = 1, max = 50)
    private String login;

    @Lob
    private byte[] photo;

    @Email(message = "A valid email is required")

    @Size(min = 5, max = 100)
    private String email;

    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters")
    private String motDePasse;

    private String numTelephone;

    private Role role;

    private Boolean actif;

    // Getters and Setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}