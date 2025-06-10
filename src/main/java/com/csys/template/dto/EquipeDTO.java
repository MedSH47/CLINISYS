package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EquipeDTO {
  private Integer id;
  private LocalDateTime dateCreation;

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

  private List<EquipePosteutilisateurDTO> equipePosteutilisateurList;

  private UtilisateurDTO chefEquipe;
 
  private List<ModuleDTO> moduleList;

  private List<UtilisateurDTO> utilisateurs;
  

  public List<UtilisateurDTO> getUtilisateurs() {
    return utilisateurs;
  }

  public void setUtilisateurs(List<UtilisateurDTO> utilisateurs) {
    this.utilisateurs = utilisateurs;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
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

  public List<EquipePosteutilisateurDTO> getEquipePosteutilisateurSet() {
    return equipePosteutilisateurList;
  }

  public void setEquipePosteutilisateurSet(List<EquipePosteutilisateurDTO> equipePosteutilisateurList) {
    this.equipePosteutilisateurList = equipePosteutilisateurList;
  }

  public UtilisateurDTO getChefEquipe() {
    return chefEquipe;
  }

  public void setChefEquipe(UtilisateurDTO chefEquipe) {
    this.chefEquipe = chefEquipe;
  }

  public List<ModuleDTO> getModuleList() {
    return moduleList;
  }

  public void setModuleList(List<ModuleDTO> moduleList) {
    this.moduleList = moduleList;
  }

  
}

