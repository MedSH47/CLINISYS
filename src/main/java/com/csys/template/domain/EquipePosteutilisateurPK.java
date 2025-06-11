package com.csys.template.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class EquipePosteutilisateurPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_poste")
    private int idPoste;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_utilisateur")
    private int idUtilisateur;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_equipe")
    private int idEquipe;

    public EquipePosteutilisateurPK() {
    }

    public EquipePosteutilisateurPK(int idPoste, int idUtilisateur, int idEquipe) {
        this.idPoste = idPoste;
        this.idUtilisateur = idUtilisateur;
        this.idEquipe = idEquipe;
    }

    public int getIdPoste() {
        return idPoste;
    }

    public void setIdPoste(int idPoste) {
        this.idPoste = idPoste;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPoste;
        hash += (int) idUtilisateur;
        hash += (int) idEquipe;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EquipePosteutilisateurPK)) {
            return false;
        }
        EquipePosteutilisateurPK other = (EquipePosteutilisateurPK) object;
        if (this.idPoste != other.idPoste) {
            return false;
        }
        if (this.idUtilisateur != other.idUtilisateur) {
            return false;
        }
        if (this.idEquipe != other.idEquipe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.domain.EquipePosteutilisateurPK[ idPoste=" + idPoste + ", idUtilisateur=" + idUtilisateur + ", idEquipe=" + idEquipe + " ]";
    }
}