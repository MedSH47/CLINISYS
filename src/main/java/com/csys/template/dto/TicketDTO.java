package com.csys.template.dto;

import com.csys.template.domain.Ticket;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class TicketDTO {
  private Integer id;
  
  @Size(
      min = 0,
      max = 200
  )
  private String titre;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String description;


  private TicketDTO parentTicket;
  private String userCreation;

  private Set<Ticket> childTickets;

  private List<CommentaireDTO> commentaireList;

  public List<CommentaireDTO> getCommentaireList() {
    return commentaireList;
}

  public void setCommentaireList(List<CommentaireDTO> commentaireList) {
    this.commentaireList = commentaireList;
  }

  @Temporal(TemporalType.DATE)
  private LocalDateTime dateCreation;

  @Size(
      min = 0,
      max = 20
  )
  private String priorite;

  @Size(
      min = 0,
      max = 20
  )
  private String statue;

  private ClientDTO idClient;

  private ModuleDTO idModule;

  private UtilisateurDTO idUtilisateur;



  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TicketDTO getParentTicket() {
        return parentTicket;
    }

    public void setParentTicket(TicketDTO parentTicket) {
        this.parentTicket = parentTicket;
    }

    public Set<Ticket> getChildTickets() {
        return childTickets;
    }

    public void setChildTickets(Set<Ticket> childTickets) {
        this.childTickets = childTickets;
    }

  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public String getPriorite() {
    return priorite;
  }

  public void setPriorite(String priorite) {
    this.priorite = priorite;
  }

  public String getStatue() {
    return statue;
  }

  public void setStatue(String statue) {
    this.statue = statue;
  }


  public ClientDTO getIdClient() {
    return idClient;
  }

  public void setIdClient(ClientDTO idClient) {
    this.idClient = idClient;
  }

  public ModuleDTO getIdModule() {
    return idModule;
  }

  public void setIdModule(ModuleDTO idModule) {
    this.idModule = idModule;
  }

  public UtilisateurDTO getIdUtilisateur() {
    return idUtilisateur;
  }

  public void setIdUtilisateur(UtilisateurDTO idUtilisateur) {
    this.idUtilisateur = idUtilisateur;
  }




}

