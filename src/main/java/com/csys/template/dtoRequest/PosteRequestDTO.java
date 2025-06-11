package com.csys.template.dtoRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PosteRequestDTO {

    @NotNull(message = "Designation cannot be null")
    @Size(min = 1, max = 100, message = "Designation must be between 1 and 100 characters")
    private String designation;

    @NotNull(message = "Actif status cannot be null")
    private Boolean actif;
    
    // Getters and Setters

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean isActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}