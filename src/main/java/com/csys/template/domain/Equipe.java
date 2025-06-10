package com.csys.template.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.csys.template.log.listener.EntityLogger;

@Entity
@Table(name = "Equipe", catalog = "Gestion_Tickets", schema = "dbo")
@EntityListeners(EntityLogger.class)

public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    @Size(max = 50)
    @Column(name = "user_creation")
    private String userCreation;
    @Size(max = 2147483647)
    @Column(name = "designation")
    private String designation;

    @ManyToOne
    @JoinColumn(name = "chef_equipe")
    private Utilisateur chefEquipe;

    @OneToMany(mappedBy = "equipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Module> moduleList;

   
    @OneToMany(mappedBy = "equipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EquipePosteutilisateur> equipePosteutilisateurList;
    
    

    public List<EquipePosteutilisateur> getEquipePosteutilisateurList() {
        return equipePosteutilisateurList;
    }

    public void setEquipePosteutilisateurList(List<EquipePosteutilisateur> equipePosteutilisateurList) {
        this.equipePosteutilisateurList = equipePosteutilisateurList;
    }

    public Utilisateur getChefEquipe() {
        return chefEquipe;
    }

    public void setChefEquipe(Utilisateur chefEquipe) {
        this.chefEquipe = chefEquipe;
    }

    public Equipe() {
    }

    public Equipe(Integer id) {
        this.id = id;
    }
     public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
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
