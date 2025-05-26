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
@Table(name = "Equipe", catalog = "Gestion_Tickets", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipe.findAll", query = "SELECT e FROM Equipe e")})
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    @Size(max = 50)
    @Column(name = "user_creation")
    private String userCreation;
    @Size(max = 2147483647)
    @Column(name = "designation")
    private String designation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipe", fetch = FetchType.LAZY)
    private Set<EquipePosteutilisateur> equipePosteutilisateurSet;
    @JoinColumn(name = "chef_equipe", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur chefEquipe;
    @OneToMany(mappedBy = "idEquipe", fetch = FetchType.LAZY)
    private Collection<Module> moduleSet;

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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @XmlTransient
    public Set<EquipePosteutilisateur> getEquipePosteutilisateurSet() {
        return equipePosteutilisateurSet;
    }

    public void setEquipePosteutilisateurSet(Set<EquipePosteutilisateur> equipePosteutilisateurSet) {
        this.equipePosteutilisateurSet = equipePosteutilisateurSet;
    }

    public Utilisateur getChefEquipe() {
        return chefEquipe;
    }

    public void setChefEquipe(Utilisateur chefEquipe) {
        this.chefEquipe = chefEquipe;
    }

    @XmlTransient
    public Collection<Module> getModuleSet() {
        return moduleSet;
    }

    public void setModuleSet(Collection<Module> moduleSet) {
        this.moduleSet = moduleSet;
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
        return "com.csys.template.config.jpa.audit.log.demain.Equipe[ id=" + id + " ]";
    }
    
}
