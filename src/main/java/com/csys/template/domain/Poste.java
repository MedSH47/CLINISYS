
package com.csys.template.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Poste")
public class Poste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "designation")
    private String designation;
    @OneToMany(mappedBy = "idPoste", fetch = FetchType.EAGER)
    private Set<EquipePoste> equipePosteCollection;
    @OneToMany(mappedBy = "idPoste", fetch = FetchType.EAGER)
    private Set<Utilisateur> utilisateurCollection;

    public Poste() {
    }

    public Poste(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @XmlTransient
    public Set<EquipePoste> getEquipePosteCollection() {
        return equipePosteCollection;
    }

    public void setEquipePosteCollection(Set<EquipePoste> equipePosteCollection) {
        this.equipePosteCollection = equipePosteCollection;
    }

    @XmlTransient
    public Set<Utilisateur> getUtilisateurCollection() {
        return utilisateurCollection;
    }

    public void setUtilisateurCollection(Set<Utilisateur> utilisateurCollection) {
        this.utilisateurCollection = utilisateurCollection;
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
        if (!(object instanceof Poste)) {
            return false;
        }
        Poste other = (Poste) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.config.jpa.audit.log.demain.Poste[ id=" + id + " ]";
    }
    
}
