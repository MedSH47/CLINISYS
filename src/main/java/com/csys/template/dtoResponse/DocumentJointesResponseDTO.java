package com.csys.template.dtoResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentJointesResponseDTO {
    
    private Integer id;
    private String extension;
    private LocalDateTime dateDocument;
    private String nomDocument;
    private Integer idTicket;
    private byte[] document; // Optional, can be omitted for performance

    // Getters and Setters
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

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
}