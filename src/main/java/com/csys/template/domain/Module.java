/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csys.template.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.csys.template.config.jpa.audit.log.listener.EntityLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "Module")
@EntityListeners(EntityLogger.class)
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    
    @Column(name = "id")
    private Integer id;
   
    @Column(name = "designation")
    private String designation;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    
    @Column(name = "creation_user")
    private String creationUser;
    @Column(name = "code")
    private Integer code;
    @OneToMany(mappedBy = "idModule" ,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Ticket> ticketList;

    public Module(Integer id,  String designation, Date creationDate,
             String creationUser, Integer code, List<Ticket> ticketList) {
        this.id = id;
        this.designation = designation;
        this.creationDate = creationDate;
        this.creationUser = creationUser;
        this.code = code;
        this.ticketList = ticketList;
    }

    public Module() {
    }

    public Module(Integer id) {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csys.template.domain.Module[ id=" + id + " ]";
    }
    
}
