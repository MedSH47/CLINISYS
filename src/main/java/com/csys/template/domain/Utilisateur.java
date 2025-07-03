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
}
