package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PosteDTO {
  @NotNull
  private Integer id;

  @Size(
      min = 0,
      max = 100
  )
  private String designation;

  private Set<EquipePosteDTO> equipePosteCollection;

  private Set<UtilisateurDTO> utilisateurCollection;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public Set<EquipePosteDTO> getEquipePosteCollection() {
    return equipePosteCollection;
  }

  public void setEquipePosteCollection(Set<EquipePosteDTO> equipePosteCollection) {
    this.equipePosteCollection = equipePosteCollection;
  }

  public Set<UtilisateurDTO> getUtilisateurCollection() {
    return utilisateurCollection;
  }

  public void setUtilisateurCollection(Set<UtilisateurDTO> utilisateurCollection) {
    this.utilisateurCollection = utilisateurCollection;
  }
}

