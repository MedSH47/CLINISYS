package com.csys.template.dto;

import com.csys.template.domain.EquipePosteutilisateurPK;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EquipePosteutilisateurDTO {
  @JsonIgnore
  private EquipePosteutilisateurPK equipePosteutilisateurPK;
  private Integer idPoste;
  private Integer idUtilisateur;
  private Integer idEquipe;
  private EquipeDTO equipe;
  private PosteDTO poste;
  private UtilisateurDTO utilisateur;

  public EquipePosteutilisateurPK getEquipePosteutilisateurPK() {
    return equipePosteutilisateurPK;
  }

  public void setEquipePosteutilisateurPK(EquipePosteutilisateurPK equipePosteutilisateurPK) {
    this.equipePosteutilisateurPK = equipePosteutilisateurPK;
    if (equipePosteutilisateurPK != null) {
      this.idPoste = equipePosteutilisateurPK.getIdPoste();
      this.idUtilisateur = equipePosteutilisateurPK.getIdUtilisateur();
      this.idEquipe = equipePosteutilisateurPK.getIdEquipe();
    }
  }

  public Integer getIdPoste() {
    return idPoste;
  }

  public void setIdPoste(Integer idPoste) {
    this.idPoste = idPoste;
    if (equipePosteutilisateurPK == null) {
      equipePosteutilisateurPK = new EquipePosteutilisateurPK();
    }
    equipePosteutilisateurPK.setIdPoste(idPoste);
  }

  public Integer getIdUtilisateur() {
    return idUtilisateur;
  }

  public void setIdUtilisateur(Integer idUtilisateur) {
    this.idUtilisateur = idUtilisateur;
    if (equipePosteutilisateurPK == null) {
      equipePosteutilisateurPK = new EquipePosteutilisateurPK();
    }
    equipePosteutilisateurPK.setIdUtilisateur(idUtilisateur);
  }

  public Integer getIdEquipe() {
    return idEquipe;
  }

  public void setIdEquipe(Integer idEquipe) {
    this.idEquipe = idEquipe;
    if (equipePosteutilisateurPK == null) {
      equipePosteutilisateurPK = new EquipePosteutilisateurPK();
    }
    equipePosteutilisateurPK.setIdEquipe(idEquipe);
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

  public UtilisateurDTO getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(UtilisateurDTO utilisateur) {
    this.utilisateur = utilisateur;
  }
}