package com.csys.template.dto;

import com.csys.template.domain.Poste;
import com.csys.template.domain.enum_identifier.Role;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

public class UtilisateurDTO {
  
  private Integer id;

  @Size(
      min = 0,
      max = 50
  )
  private String nom;

  @Size(
      min = 0,
      max = 50
  )
  private String prenom;

  private BigInteger numTelephone;

  @Size(
      min = 0,
      max = 100
  )
  private String email;

  @Size(
      min = 0,
      max = 50
  )
  private String userCreation;

  @Temporal(TemporalType.DATE)
  private Date dateCreation;


  private String motDePasse;

  @Enumerated(EnumType.STRING)
  private Role role;

  private Boolean activite;

  private Poste idPoste;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public BigInteger getNumTelephone() {
    return numTelephone;
  }

  public void setNumTelephone(BigInteger numTelephone) {
    this.numTelephone = numTelephone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserCreation() {
    return userCreation;
  }

  public void setUserCreation(String userCreation) {
    this.userCreation = userCreation;
  }

  public Date getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(Date dateCreation) {
    this.dateCreation = dateCreation;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Boolean getActivite() {
    return activite;
  }

  public void setActivite(Boolean activite) {
    this.activite = activite;
  }

  public Poste getIdPoste() {
    return idPoste;
  }

  public void setIdPoste(Poste idPoste) {
    this.idPoste = idPoste;
  }
}

