package com.csys.template.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import com.csys.template.log.listener.EntityLogger;

@Entity
@Table(name = "document_jointes", catalog = "Gestion_Tickets", schema = "dbo")
@EntityListeners(EntityLogger.class)
public class DocumentJointes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(max = 10)
    @Column(name = "extension")
    private String extension;
    
    @Lob
    @Column(name = "document")
    private byte[] document;
    
    @Column(name = "date_document")
    private LocalDateTime dateDocument;
    
    @Size(max = 2147483647)
    @Column(name = "nom_document")
    private String nomDocument;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;

    public DocumentJointes() {}

    public DocumentJointes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public LocalDateTime getDateDocument() {
        return dateDocument;
    }

    public void setDateDocument(LocalDateTime dateDocument) {
        this.dateDocument = dateDocument;
    }

    public String getNomDocument() {
        return nomDocument;
    }

    public void setNomDocument(String nomDocument) {
        this.nomDocument = nomDocument;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DocumentJointes)) {
            return false;
        }
        DocumentJointes other = (DocumentJointes) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}