
package com.csys.template.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.csys.template.domain.enum_identifier.Role;

/**
 *
 * @author harra
 */
@Entity
@Table(name = "Utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "login")
    private String login;
    @Size(max = 30)
    @Column(name = "password")
    private String password;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Size(max = 30)
    @Column(name = "creation_user")
    private String creationUser;
    @Column(name = "actif")
    private Boolean actif;
    @Size(max = 30)
    @Column(name = "role")
    private Role role;
    @JoinColumn(name = "id_equip", referencedColumnName = "id")
    @ManyToOne
    private Equipe idEquip;
    @JoinColumn(name = "id_poste", referencedColumnName = "id")
    @ManyToOne
    private Poste idPoste;

    public Utilisateur(@NotNull Integer id, @Size(max = 30) String login, @Size(max = 30) String password,
            Date creationDate, @Size(max = 30) String creationUser, Boolean actif, @Size(max = 30) Role role,
            Equipe idEquip, Poste idPoste) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.creationDate = creationDate;
        this.creationUser = creationUser;
        this.actif = actif;
        this.role = role;
        this.idEquip = idEquip;
        this.idPoste = idPoste;
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
        return "com.csys.template.domain.Utilisateur[ id=" + id + " ]";
    }
    
}
