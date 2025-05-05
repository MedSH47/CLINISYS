package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PosteDTO {
  @NotNull
  private Integer id;


  private String designation;

  private Integer code;

  private List utilisateurList;


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

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public List getUtilisateurList() {
    return utilisateurList;
  }

  public void setUtilisateurList(List utilisateurList) {
    this.utilisateurList = utilisateurList;
  }
}

