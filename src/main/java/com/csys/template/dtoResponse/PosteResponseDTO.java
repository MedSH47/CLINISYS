package com.csys.template.dtoResponse;

import java.time.LocalDateTime;

public class PosteResponseDTO {
  private Integer id;
  private String designation;
  private Boolean actif;
  private LocalDateTime dateCreation;
  private String userCreation;


  // Getters and Setters

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

  public Boolean isActif() {
    return actif;
  }

  public void setActif(Boolean actif) {
    this.actif = actif;
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
}