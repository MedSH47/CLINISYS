/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.csys.template.config.jpa.audit.log.listener.EntityLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Equipe")
@EntityListeners(EntityLogger.class)
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom_equipe")
    private String nomEquipe;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "id_utilisateur_chef")
    @JsonIgnore
    private Utilisateur chef;

    @Column(name = "creation_user")
    private String creationUser;
    @OneToMany(mappedBy = "idEquip", fetch = FetchType.LAZY)
    @JsonIgnore // Prevent infinite recursion (optional if using DTOs)
    private List<Ticket> ticketList;

    @OneToMany(mappedBy = "idEquip", fetch = FetchType.LAZY)
    @JsonIgnore // Prevent infinite recursion (optional if using DTOs)
    private List<Utilisateur> utilisateurList;

    public Equipe(Integer id, String nomEquipe, Date creationDate,
            String creationUser, List<Ticket> ticketList, List<Utilisateur> utilisateurList, Utilisateur chef) {
        this.id = id;
        this.nomEquipe = nomEquipe;
        this.creationDate = creationDate;
        this.creationUser = creationUser;
        this.ticketList = ticketList;
        this.utilisateurList = utilisateurList;
        this.chef = chef;
    }

    public Equipe() {
    }

    public Equipe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utilisateur getChef() {
        return chef;
    }

    public void setChef(Utilisateur chef) {
        this.chef = chef;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
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

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<Utilisateur> getUtilisateurList() {
        return utilisateurList;
    }

    public void setUtilisateurList(List<Utilisateur> utilisateurList) {
        this.utilisateurList = utilisateurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Equipe)) {
            return false;
        }
        Equipe other = (Equipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.domain.Equipe[ id=" + id + " ]";
    }

}
