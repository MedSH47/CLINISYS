package com.csys.template.dtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public class EquipeResponseDTO {
    private Integer id;
    private String designation;
    private LocalDateTime dateCreation;
    private String userCreation;
    
    // These will be populated with "light" DTOs
    private UtilisateurResponseDTO chefEquipe;
    private List<ModuleResponseDTO> moduleList;
    private List<UtilisateurResponseDTO> utilisateurs;
    private Boolean actif;
    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getActif() {
        return actif;
    }
    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public UtilisateurResponseDTO getChefEquipe() {
        return chefEquipe;
    }

    public void setChefEquipe(UtilisateurResponseDTO chefEquipe) {
        this.chefEquipe = chefEquipe;
    }

    public List<ModuleResponseDTO> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<ModuleResponseDTO> moduleList) {
        this.moduleList = moduleList;
    }

    public List<UtilisateurResponseDTO> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<UtilisateurResponseDTO> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}