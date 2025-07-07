package com.csys.template.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import com.csys.template.domain.enum_identifier.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@Entity
@Table(name = "Utilisateur", catalog = "Gestion_Tickets", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
  @Audited 

public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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

    @Column(name = "login", unique = true)
    private String login;

    @Size(max = 50)
    @Column(name = "user_creation")
    private String userCreation;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Size(max = 2147483647)
    @NotNull
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "activite")
    private Boolean actif;
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipePosteutilisateur> equipePosteutilisateurList;

    @OneToMany(mappedBy = "idUtilisateur", fetch = FetchType.LAZY)
    private List<Ticket> ticketList;

    public static long getSerialversionuid() {
      return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
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
    public List<EquipePosteutilisateur> getEquipePosteutilisateurList() {
        return equipePosteutilisateurList;
    }
    public void setEquipePosteutilisateurList(List<EquipePosteutilisateur> equipePosteutilisateurList) {
        this.equipePosteutilisateurList = equipePosteutilisateurList;
    }
    public List<Ticket> getTicketList() {
        return ticketList;
    }
    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
    public Utilisateur(Integer id) {
        this.id = id;
    }
    public Utilisateur(String nom, String prenom, String numTelephone, String email, String login, String userCreation, LocalDateTime dateCreation, String motDePasse, byte[] photo, Role role, Boolean actif) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTelephone = numTelephone;
        this.email = email;
        this.login = login;
        this.userCreation = userCreation;
        this.dateCreation = dateCreation;
        this.motDePasse = motDePasse;
        this.photo = photo;
        this.role = role;
        this.actif = actif;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}
