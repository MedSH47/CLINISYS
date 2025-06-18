package com.csys.template.dtoResponse;

import com.csys.template.domain.enum_identifier.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UtilisateurResponseDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String login;
    private String email;
    private String numTelephone;
    private Role role;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private String userCreation;
    private byte[] photo;

    // Simplified collection to avoid circular dependencies
    private Collection<EquipePosteDTO> equipePosteSet; 
    
    // Will be populated with "light" TicketResponseDTOs
    private List<TicketResponseDTO> ticketList;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Collection<EquipePosteDTO> getEquipePosteSet() {
        return equipePosteSet;
    }

    public void setEquipePosteSet(Collection<EquipePosteDTO> equipePosteSet) {
        this.equipePosteSet = equipePosteSet;
    }

    public List<TicketResponseDTO> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketResponseDTO> ticketList) {
        this.ticketList = ticketList;
    }
}