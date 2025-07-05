package com.csys.template.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Ticket", catalog = "Gestion_Tickets", schema = "dbo")
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
  @Audited 

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "actif")
    private Boolean actif;
    @Column(name = "date_echeance")
    private LocalDateTime date_echeance;
  

    @ManyToOne
    @JoinColumn(name = "id_ticket_parent")
    private Ticket parentTicket;

    @OneToMany(mappedBy = "parentTicket", cascade = CascadeType.ALL)
    private Set<Ticket> childTickets = new HashSet<>();

    @Size(max = 200)
    @Column(name = "titre")
    private String titre;

    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;

    @Size(max = 50)
    @Column(name = "user_creation")
    private String userCreation;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "priorite")
    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    @Column(name = "statue")
           @Enumerated(EnumType.STRING)
    private Status statue;

    @Column(name = "debut_traitement")
    private LocalDateTime debutTraitement;
    
    // @Column(name = "id_module")
    // private Integer idModule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_module")
    private Module module;

    @JoinColumn(name = "id_utilisateur", nullable = true, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Ticket_Utilisateur", foreignKeyDefinition = "FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id) ON DELETE SET NULL"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur idUtilisateur;

    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client idClient;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<Commentaire> commentaireList;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<DocumentJointes> documentJointesList;

    @Column(name = "date_cloture") // Nom de la colonne dans votre DB
    private LocalDateTime dateCloture;

    // ... (autres champs et getters/setters) ...

    public LocalDateTime getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }
    public LocalDateTime getDebutTraitement() {
        return debutTraitement;
    }
    public void setDebutTraitement(LocalDateTime debutTraitement) {
        this.debutTraitement = debutTraitement;
    }

    public List<Commentaire> getCommentaireList() {
        return commentaireList;
    }

    public void setCommentaireList(List<Commentaire> commentaireList) {
        this.commentaireList = commentaireList;
    }

    public List<DocumentJointes> getDocumentJointesList() {
        return documentJointesList;
    }

    public void setDocumentJointesList(List<DocumentJointes> documentJointesList) {
        this.documentJointesList = documentJointesList;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Client getIdClient() {
        return idClient;
    }
  
    public LocalDateTime getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(LocalDateTime date_echeance) {
        this.date_echeance = date_echeance;
    }
    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Utilisateur getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Utilisateur idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Ticket() {
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ticket getParentTicket() {
        return parentTicket;
    }

    public void setParentTicket(Ticket parentTicket) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.domain.Ticket[ id=" + id + " ]";
    }
}