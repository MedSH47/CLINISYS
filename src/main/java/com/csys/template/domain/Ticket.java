/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_ticket_parent")
    private Integer idTicketParent;
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
    @Column(name = "statue")
    private String statue;
    @OneToMany(mappedBy = "idTicket", fetch = FetchType.EAGER)
    private Set<Avancement> avancementCollection;
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Client idClient;
    @JoinColumn(name = "id_module", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Module idModule;
    @OneToMany(mappedBy = "idTicket", fetch = FetchType.EAGER)
    private Set<Ticketfichier> ticketfichierCollection;

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

    public Integer getIdTicketParent() {
        return idTicketParent;
    }

    public void setIdTicketParent(Integer idTicketParent) {
        this.idTicketParent = idTicketParent;
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

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    @XmlTransient
    public Set<Avancement> getAvancementCollection() {
        return avancementCollection;
    }

    public void setAvancementCollection(Set<Avancement> avancementCollection) {
        this.avancementCollection = avancementCollection;
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

    @XmlTransient
    public Set<Ticketfichier> getTicketfichierCollection() {
        return ticketfichierCollection;
    }

    public void setTicketfichierCollection(Set<Ticketfichier> ticketfichierCollection) {
        this.ticketfichierCollection = ticketfichierCollection;
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
