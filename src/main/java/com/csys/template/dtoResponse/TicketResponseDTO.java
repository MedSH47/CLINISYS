package com.csys.template.dtoResponse;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketResponseDTO {

    private Integer id;
    private String titre;
    private String description;
    private String userCreation;
    private LocalDateTime dateCreation;
    private LocalDateTime date_echeance;

    private Priorite priorite;
    private Status statue;
    private TicketResponseDTO parentTicket;
    private List<TicketResponseDTO> childTickets;
    private ClientResponseDTO idClient;
    private ModuleResponseDTO idModule;
    private UtilisateurResponseDTO idUtilisateur;
    private List<CommentaireResponseDTO> commentaireList;
    private List<DocumentJointesResponseDTO> documentJointesList;
    private Boolean actif;

    // Getters and Setters

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public List<DocumentJointesResponseDTO> getDocumentJointesList() {
        return documentJointesList;
    }

    public void setDocumentJointesList(List<DocumentJointesResponseDTO> documentJointesList) {
        this.documentJointesList = documentJointesList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public Status getStatue() {
        return statue;
    }

    public void setStatue(Status statue) {
        this.statue = statue;
    }

    public LocalDateTime getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(LocalDateTime date_echeance) {
        this.date_echeance = date_echeance;
    }

    public TicketResponseDTO getParentTicket() {
        return parentTicket;
    }

    public void setParentTicket(TicketResponseDTO parentTicket) {
        this.parentTicket = parentTicket;
    }

    public List<TicketResponseDTO> getChildTickets() {
        return childTickets;
    }

    public void setChildTickets(List<TicketResponseDTO> childTickets) {
        this.childTickets = childTickets;
    }

    public ClientResponseDTO getIdClient() {
        return idClient;
    }

    public void setIdClient(ClientResponseDTO idClient) {
        this.idClient = idClient;
    }

    public ModuleResponseDTO getIdModule() {
        return idModule;
    }

    public void setIdModule(ModuleResponseDTO idModule) {
        this.idModule = idModule;
    }

    public UtilisateurResponseDTO getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(UtilisateurResponseDTO idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public List<CommentaireResponseDTO> getCommentaireList() {
        return commentaireList;
    }

    public void setCommentaireList(List<CommentaireResponseDTO> commentaireList) {
        this.commentaireList = commentaireList;
    }
}