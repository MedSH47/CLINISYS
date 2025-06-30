package com.csys.template.dtoResponse;


public class EquipePosteutilisateurResponseDTO {

    // Using "light" DTOs for the nested objects
    private PosteResponseDTO poste;
    private UtilisateurResponseDTO utilisateur;
    private EquipeResponseDTO equipe;

    // Getters and Setters
    public PosteResponseDTO getPoste() {
        return poste;
    }

    public void setPoste(PosteResponseDTO poste) {
        this.poste = poste;
    }

    public UtilisateurResponseDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurResponseDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public EquipeResponseDTO getEquipe() {
        return equipe;
    }

    public void setEquipe(EquipeResponseDTO equipe) {
        this.equipe = equipe;
    }
}