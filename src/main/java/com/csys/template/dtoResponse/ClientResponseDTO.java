package com.csys.template.dtoResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDTO {

    private Integer id;
    private String nomComplet;
    private String adress;
    private String email;
    private String region;
    private LocalDateTime dateCreation;
    private String userCreation;
    private Boolean actif;
    
    // This will be a list of "light" TicketResponseDTOs
    @JsonIgnore
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
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
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