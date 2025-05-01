package com.csys.template.dto;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class ClientDTO {
  private Integer id;
  private Integer numClient;


  private String adress;

 
  private String telephone;

  private String nom;

  
  private String prenom;

  @Temporal(TemporalType.DATE)
  private Date creationDate;

 
  private String creationUser;

  private List ticketList;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getNumClient() {
    return numClient;
  }

  public void setNumClient(Integer numClient) {
    this.numClient = numClient;
  }

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
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

  public List getTicketList() {
    return ticketList;
  }

  public void setTicketList(List ticketList) {
    this.ticketList = ticketList;
  }
}

