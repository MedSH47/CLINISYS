package com.csys.template.dto;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.Poste;
import com.csys.template.domain.enum_identifier.Role;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UtilisateurDTO {
  @NotNull
  private Integer id;

  @Size(
      min = 0,
      max = 30
  )
  private String login;

  @Size(
      min = 0,
      max = 30
  )
  private String password;

  @Temporal(TemporalType.DATE)
  private Date creationDate;

  @Size(
      min = 0,
      max = 30
  )
  private String creationUser;

  private Boolean actif;

  @Size(
      min = 0,
      max = 30
  )
  private Role role;

  private Equipe idEquip;

  private Poste idPoste;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public String getCreationUser() {
    return creationUser;
  }

  public void setCreationUser(String creationUser) {
    this.creationUser = creationUser;
  }

  public Boolean getActif() {
    return actif;
  }

  public void setActif(Boolean actif) {
    this.actif = actif;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Equipe getIdEquip() {
    return idEquip;
  }

  public void setIdEquip(Equipe idEquip) {
    this.idEquip = idEquip;
  }

  public Poste getIdPoste() {
    return idPoste;
  }

  public void setIdPoste(Poste idPoste) {
    this.idPoste = idPoste;
  }
}

