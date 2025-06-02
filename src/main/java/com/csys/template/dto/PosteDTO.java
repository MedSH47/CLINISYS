package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

public class PosteDTO {
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
      max = 100
  )
  private String designation;

  private Collection<EquipePosteutilisateurDTO> equipePosteutilisateurSet;

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

  public Collection<EquipePosteutilisateurDTO> getEquipePosteutilisateurSet() {
    return equipePosteutilisateurSet;
  }

  public void setEquipePosteutilisateurSet(Collection<EquipePosteutilisateurDTO> equipePosteutilisateurSet) {
    this.equipePosteutilisateurSet = equipePosteutilisateurSet;
  }
}

