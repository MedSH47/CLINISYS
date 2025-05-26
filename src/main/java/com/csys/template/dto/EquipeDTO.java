package com.csys.template.dto;

import com.csys.template.domain.Utilisateur;
import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

public class EquipeDTO {
  private Integer id;

  @Temporal(TemporalType.DATE)
  private Date dateCreation;

  @Size(
      min = 0,
      max = 50
  )
  private String userCreation;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String designation;

  private Collection<EquipePosteutilisateurDTO> equipePosteutilisateurSet;

  private UtilisateurDTO chefEquipe;

  private Collection<ModuleDTO> moduleSet;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(Date dateCreation) {
    this.dateCreation = dateCreation;
  }

  public String getUserCreation() {
    return userCreation;
  }

  public void setUserCreation(String userCreation) {
    this.userCreation = userCreation;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public Collection getEquipePosteutilisateurSet() {
    return equipePosteutilisateurSet;
  }

  public void setEquipePosteutilisateurSet(Collection equipePosteutilisateurSet) {
    this.equipePosteutilisateurSet = equipePosteutilisateurSet;
  }

  public UtilisateurDTO getChefEquipe() {
    return chefEquipe;
  }

  public void setChefEquipe(UtilisateurDTO chefEquipe) {
    this.chefEquipe = chefEquipe;
  }

  public Collection<ModuleDTO> getModuleSet() {
    return moduleSet;
  }

  public void setModuleSet(Collection<ModuleDTO> moduleSet) {
    this.moduleSet = moduleSet;
  }

  
}

