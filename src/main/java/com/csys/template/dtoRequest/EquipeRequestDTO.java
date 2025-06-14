package com.csys.template.dtoRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EquipeRequestDTO {

    @NotNull(message = "Designation cannot be null")
    @Size(min = 2, max = 255, message = "Designation must be between 2 and 255 characters")
    private String designation;

    private Integer idChefEquipe;

    private Boolean actif ;

    // Getters and Setters
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getIdChefEquipe() {
        return idChefEquipe;
    }

    public void setIdChefEquipe(Integer idChefEquipe) {
        this.idChefEquipe = idChefEquipe;
    }
    public Boolean getActif() {
        return actif;
    }
    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}