/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author harra
 */
@Entity
@Table(name = "Ticket", catalog = "Gestion_Tickets", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_ticket_parent")
    private Ticket parentTicket;

    // Enfants de ce ticket (inverse side)
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
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    @Size(max = 20)
    @Column(name = "priorite")
    private String priorite;
    @Size(max = 20)
    @Column(name = "statue")
    private String statue;
    @OneToMany(mappedBy = "idTicket", fetch = FetchType.LAZY)
    private Set<Avancement> avancementSet;
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client idClient;
    @JoinColumn(name = "id_module", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Module idModule;
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur idUtilisateur;
    @OneToMany(mappedBy = "idTicket", fetch = FetchType.LAZY)
    private Set<DocumentJointes> documentJointesSet;
    @OneToMany(mappedBy = "idTicket", fetch = FetchType.LAZY)
    private Set<Commentaire> commentaireSet;
    @OneToMany(mappedBy = "idTicket", fetch = FetchType.LAZY)
    private Set<Ticketfichier> ticketfichierSet;

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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
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

    @XmlTransient
    public Set<Avancement> getAvancementSet() {
        return avancementSet;
    }

    public void setAvancementSet(Set<Avancement> avancementSet) {
        this.avancementSet = avancementSet;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Module getIdModule() {
        return idModule;
    }

    public void setIdModule(Module idModule) {
        this.idModule = idModule;
    }

    public Utilisateur getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Utilisateur idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @XmlTransient
    public Set<DocumentJointes> getDocumentJointesSet() {
        return documentJointesSet;
    }

    public void setDocumentJointesSet(Set<DocumentJointes> documentJointesSet) {
        this.documentJointesSet = documentJointesSet;
    }

    @XmlTransient
    public Set<Commentaire> getCommentaireSet() {
        return commentaireSet;
    }

    public void setCommentaireSet(Set<Commentaire> commentaireSet) {
        this.commentaireSet = commentaireSet;
    }

    @XmlTransient
    public Set<Ticketfichier> getTicketfichierSet() {
        return ticketfichierSet;
    }

    public void setTicketfichierSet(Set<Ticketfichier> ticketfichierSet) {
        this.ticketfichierSet = ticketfichierSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "com.csys.template.config.jpa.audit.log.demain.Ticket[ id=" + id + " ]";
    }
    
}
