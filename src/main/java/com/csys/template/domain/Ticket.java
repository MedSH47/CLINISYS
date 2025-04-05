package com.csys.template.domain;

 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ticket_number", nullable = false)
    private String ticketNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "module", length = 50)
    private String module;

    @Column(name = "priority", length = 20)
    private String priority;

    @Column(name = "temperature", length = 20)
    private String temperature;

    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    public Ticket(Integer id, String ticketNumber, Date date, String description, String module, String priority,
            String temperature, String status, Client client, Equipe equipe) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.date = date;
        this.description = description;
        this.module = module;
        this.priority = priority;
        this.temperature = temperature;
        this.status = status;
        this.client = client;
        this.equipe = equipe;
    }

    public Ticket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    
}