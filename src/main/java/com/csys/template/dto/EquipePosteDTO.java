package com.csys.template.dto;

public class EquipePosteDTO {
     private EquipeDTO equipe;
    private PosteDTO poste;
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public EquipeDTO getEquipe() {
        return equipe;
    }
    public void setEquipe(EquipeDTO equipe) {
        this.equipe = equipe;
    }
    public PosteDTO getPoste() {
        return poste;
    }
    public void setPoste(PosteDTO poste) {
        this.poste = poste;
    }
}
