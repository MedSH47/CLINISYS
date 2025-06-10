package com.csys.template.dto;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;


import com.csys.template.domain.enum_identifier.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

  private String numTelephone;

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

  private LocalDateTime dateCreation;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String motDePasse;

  


  private byte[] photo;

 
  @Enumerated(EnumType.STRING)
  private Role role;

  private Boolean activite;

  private List<TicketDTO> ticketList;

 
  private Collection<EquipePosteutilisateurDTO> equipePosteutilisateurSet;

  
  private String login;

  private Collection<EquipePosteDTO> equipePosteSet;

  public Collection<EquipePosteDTO> getEquipePosteSet() {
    return equipePosteSet;
  }

  public void setEquipePosteSet(Collection<EquipePosteDTO> equipePosteSet) {
    this.equipePosteSet = equipePosteSet;
  }


  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

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

  public String getNumTelephone() {
    return numTelephone;
  }

  public void setNumTelephone(String numTelephone) {
    this.numTelephone = numTelephone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
   public List<TicketDTO> getTicketList() {
    return ticketList;
  }

  public void setTicketList(List<TicketDTO> ticketList) {
    this.ticketList = ticketList;
  }


  public String getUserCreation() {
    return userCreation;
  }

  public void setUserCreation(String userCreation) {
    this.userCreation = userCreation;
  }

  public LocalDateTime getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
    this.dateCreation = dateCreation;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }



  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
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

 

  public Collection<EquipePosteutilisateurDTO> getEquipePosteutilisateurSet() {
    return equipePosteutilisateurSet;
  }

  public void setEquipePosteutilisateurSet(Collection<EquipePosteutilisateurDTO> equipePosteutilisateurSet) {
    this.equipePosteutilisateurSet = equipePosteutilisateurSet;
  }

}

