package com.csys.template.dtoRequest;

import javax.validation.constraints.NotNull;

public class EquipePosteutilisateurRequestDTO {

    @NotNull
    private Integer idPoste;

    @NotNull
    private Integer idUtilisateur;
    
    @NotNull
    private Integer idEquipe;

    // Getters and Setters
    public Integer getIdPoste() {
        return idPoste;
    }

    public void setIdPoste(Integer idPoste) {
        this.idPoste = idPoste;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }
}