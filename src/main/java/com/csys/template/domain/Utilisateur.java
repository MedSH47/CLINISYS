/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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

import com.csys.template.domain.enum_identifier.Role;

/**
 *
 * @author harra
 */
@Entity
@Table(name = "Utilisateur", catalog = "Gestion_Tickets", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u")})
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nom")
    private String nom;
    @Size(max = 50)
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "num_telephone")
    private BigInteger numTelephone;
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Column(name = "login" ,unique = true)
    private String login;
   

    @Size(max = 50)
    @Column(name = "user_creation")
    private String userCreation;
    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    @Size(max = 2147483647)
    @Column(name = "mot_de_passe")
    private String motDePasse;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "activite")
    private Boolean activite;
    @OneToMany(mappedBy = "idUtilisateur", fetch = FetchType.LAZY)
    private Collection<Ticket> ticketSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private Set<EquipePosteutilisateur> equipePosteutilisateurSet;
    @OneToMany(mappedBy = "chefEquipe", fetch = FetchType.LAZY)
    private Set<Equipe> equipeSet;

    public Utilisateur() {
    }

    public Utilisateur(Integer id) {
        this.id = id;
    }

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

    @XmlTransient
    public Collection<Ticket> getTicketSet() {
        return ticketSet;
    }

    public void setTicketSet(Collection<Ticket> ticketSet) {
        this.ticketSet = ticketSet;
    }

    @XmlTransient
    public Set<EquipePosteutilisateur> getEquipePosteutilisateurSet() {
        return equipePosteutilisateurSet;
    }

    public void setEquipePosteutilisateurSet(Set<EquipePosteutilisateur> equipePosteutilisateurSet) {
        this.equipePosteutilisateurSet = equipePosteutilisateurSet;
    }

    @XmlTransient
    public Set<Equipe> getEquipeSet() {
        return equipeSet;
    }

    public void setEquipeSet(Set<Equipe> equipeSet) {
        this.equipeSet = equipeSet;
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
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.config.jpa.audit.log.demain.Utilisateur[ id=" + id + " ]";
    }
    
}
