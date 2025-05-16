package com.csys.template.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.csys.template.config.jpa.audit.log.listener.EntityLogger;
import com.csys.template.domain.enum_identifier.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Utilisateur")
@EntityListeners(EntityLogger.class)
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;
    @JsonIgnore
    @OneToOne(mappedBy = "chef")
    private Equipe chefEquipe;

    @Column(name = "password")
    private String password;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "creation_user")
    private String creationUser;

    @Column(name = "actif")
    private Boolean actif = false;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "cin")
    private Integer cin;

    @Column(name = "telephone")
    private Integer telephone;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @JoinColumn(name = "id_equip", referencedColumnName = "id")
    @ManyToOne
    private Equipe idEquip;

    @JoinColumn(name = "id_poste", referencedColumnName = "id")
    @ManyToOne
    private Poste idPoste;

    @OneToMany(mappedBy = "collaborateur", fetch = FetchType.EAGER)
    @JsonIgnore     
    private List<Ticket> tickets = new ArrayList<>();

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    // Constructors
    public Utilisateur() {
    }

    public Utilisateur(Integer id, String login, String password, Date creationDate, String creationUser, Boolean actif,
            String nom, String prenom, Integer cin, Role role, Equipe idEquip, Poste idPoste, Integer telephone,
            List<Ticket> tickets, Equipe chefEquipe) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.creationDate = creationDate;
        this.creationUser = creationUser;
        this.actif = actif;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.role = role;
        this.idEquip = idEquip;
        this.idPoste = idPoste;
        this.telephone = telephone;
        this.tickets = tickets;
        this.chefEquipe = chefEquipe;
    }

    // Getters and Setters
    public Equipe getChefEquipe() {
        return chefEquipe;
    }

    public void setChefEquipe(Equipe chefEquipe) {
        this.chefEquipe = chefEquipe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Ticket> getTicket() {
        return tickets;
    }

    public void setTicket(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
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

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Equipe getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Equipe idEquip) {
        this.idEquip = idEquip;
    }

    public Poste getIdPoste() {
        return idPoste;
    }

    public void setIdPoste(Poste idPoste) {
        this.idPoste = idPoste;
    }

    // Other methods
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
        return "com.csys.template.domain.Utilisateur[ id=" + id + " ]";
    }
}