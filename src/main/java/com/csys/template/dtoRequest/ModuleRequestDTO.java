package com.csys.template.dtoRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ModuleRequestDTO {

    @NotNull(message = "Designation cannot be null")
    @Size(min = 1, max = 100, message = "Designation must be between 1 and 100 characters")
    private String designation;

    private Integer idEquipe;

    private Boolean actif ;
    


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

    public Integer getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }
}