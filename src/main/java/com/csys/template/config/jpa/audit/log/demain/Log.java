package com.csys.template.config.jpa.audit.log.demain;


import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "entity_name")
    private String entityName;
    
    @Column(name = "entity_id")
    private String entityId; // Added
    
    @Column(name = "action")
    private String action;
    
    @Column(name = "performed_by")
    private String performedBy;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(length = 1000, name = "details")
    private String details;
    
    @Column(name = "ip_address")
    private String ipAddress; // Added
    
    @Column(name = "operation_type")
    private String operationType; // Added (e.g., HTTP method)
    
    @Column(name = "old_state", columnDefinition = "TEXT")
    private String oldState; // Added
    
    @Column(name = "new_state", columnDefinition = "TEXT")
    private String newState; // Added

    // Constructors
    public Log() {}

    public Log(String entityName, String action, String performedBy, 
              LocalDateTime timestamp, String details) {
        this.entityName = entityName;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOldState() {
        return oldState;
    }

    public void setOldState(String oldState) {
        this.oldState = oldState;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }



  
}