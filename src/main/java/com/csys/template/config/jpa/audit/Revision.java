package com.csys.template.config.jpa.audit;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name = "revision") // Correspond au nom de table de votre superviseur
@RevisionEntity(AuditListener.class) // Lie cette entité au listener que nous créerons ensuite
public class Revision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @RevisionNumber // Indique que c'est le numéro de révision
    private int id;

    @RevisionTimestamp // Indique que c'est l'horodatage de la révision
    private Date timestamp;

    private String userCreate; // Stockera le nom de l'utilisateur

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }
}