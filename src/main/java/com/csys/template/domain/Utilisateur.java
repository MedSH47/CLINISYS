package com.csys.template.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import com.csys.template.domain.enum_identifier.Role;
import com.csys.template.log.listener.EntityLogger;

@Entity
@Table(name = "Utilisateur", catalog = "Gestion_Tickets", schema = "dbo")
@EntityListeners(EntityLogger.class)
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
    private String numTelephone;

    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Column(name = "login" ,unique = true)
    private String login;

    @Size(max = 50)
    @Column(name = "user_creation")
    private String userCreation;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

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

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<EquipePosteutilisateur> equipePosteutilisateurList;
    
    @OneToMany(mappedBy = "idUtilisateur", fetch = FetchType.LAZY)
    private List<Ticket> ticketList;

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<EquipePosteutilisateur> getEquipePosteutilisateurList() {
        return equipePosteutilisateurList;
    }

    public void setEquipePosteutilisateurList(List<EquipePosteutilisateur> equipePosteutilisateurList) {
        this.equipePosteutilisateurList = equipePosteutilisateurList;
    }

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