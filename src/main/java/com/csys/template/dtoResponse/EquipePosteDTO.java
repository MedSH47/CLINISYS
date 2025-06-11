package com.csys.template.dtoResponse;

public class EquipePosteDTO {
     private EquipeResponseDTO equipe;
    private PosteResponseDTO poste;
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public EquipeResponseDTO getEquipe() {
        return equipe;
    }
    public void setEquipe(EquipeResponseDTO equipe) {
        this.equipe = equipe;
    }
    public PosteResponseDTO getPoste() {
        return poste;
    }
    public void setPoste(PosteResponseDTO poste) {
        this.poste = poste;
    }
}
