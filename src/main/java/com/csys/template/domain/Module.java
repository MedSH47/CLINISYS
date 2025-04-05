package com.csys.template.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modules")
public class Module implements Serializable{

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Integer id;

    
    @Column(name = "nom_du_module", nullable=false)
    private String name;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    




}
