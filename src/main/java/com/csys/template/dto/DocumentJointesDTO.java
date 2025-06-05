package com.csys.template.dto;

import com.csys.template.domain.Ticket;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DocumentJointesDTO {
  private Integer id;

  @Size(
      min = 0,
      max = 10
  )
  private String extension;

  private byte[] document;

  @Temporal(TemporalType.DATE)
  private Date dateDocument;

  @Size(
      min = 0,
      max = 2147483647
  )
  private String nomDocument;

  private Ticket idTicket;

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

  public Date getDateDocument() {
    return dateDocument;
  }

  public void setDateDocument(Date dateDocument) {
    this.dateDocument = dateDocument;
  }

  public String getNomDocument() {
    return nomDocument;
  }

  public void setNomDocument(String nomDocument) {
    this.nomDocument = nomDocument;
  }

  public Ticket getIdTicket() {
    return idTicket;
  }

  public void setIdTicket(Ticket idTicket) {
    this.idTicket = idTicket;
  }
}

