/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;


@Entity
@Table(name = "Equipe_Poste_utilisateur", catalog = "Gestion_Tickets", schema = "dbo")

public class EquipePosteutilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EquipePosteutilisateurPK equipePosteutilisateurPK;
    @JoinColumn(name = "id_equipe", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Equipe equipe;
    @JoinColumn(name = "id_poste", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poste poste;
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Utilisateur utilisateur;

    public EquipePosteutilisateur() {
    }

    public EquipePosteutilisateur(EquipePosteutilisateurPK equipePosteutilisateurPK) {
        this.equipePosteutilisateurPK = equipePosteutilisateurPK;
    }

    public EquipePosteutilisateur(int idPoste, int idUtilisateur, int idEquipe) {
        this.equipePosteutilisateurPK = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
    }

    public EquipePosteutilisateurPK getEquipePosteutilisateurPK() {
        return equipePosteutilisateurPK;
    }

    public void setEquipePosteutilisateurPK(EquipePosteutilisateurPK equipePosteutilisateurPK) {
        this.equipePosteutilisateurPK = equipePosteutilisateurPK;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipePosteutilisateurPK != null ? equipePosteutilisateurPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquipePosteutilisateur)) {
            return false;
        }
        EquipePosteutilisateur other = (EquipePosteutilisateur) object;
        if ((this.equipePosteutilisateurPK == null && other.equipePosteutilisateurPK != null) || (this.equipePosteutilisateurPK != null && !this.equipePosteutilisateurPK.equals(other.equipePosteutilisateurPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.config.jpa.audit.log.demain.EquipePosteutilisateur[ equipePosteutilisateurPK=" + equipePosteutilisateurPK + " ]";
    }
    
}
