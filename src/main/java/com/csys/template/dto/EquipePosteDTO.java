package com.csys.template.dto;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.Poste;
import java.lang.Integer;
import javax.validation.constraints.NotNull;

public class EquipePosteDTO {
  @NotNull
  private Integer id;

  private Equipe idEquipe;

  private Poste idPoste;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Equipe getIdEquipe() {
    return idEquipe;
  }

  public void setIdEquipe(Equipe idEquipe) {
    this.idEquipe = idEquipe;
  }

  public Poste getIdPoste() {
    return idPoste;
  }

  public void setIdPoste(Poste idPoste) {
    this.idPoste = idPoste;
  }
}

