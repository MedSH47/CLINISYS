package com.csys.template.log.demain;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "log")
public class Log {

    public enum LogType {
        AUDIT, NOTIFICATION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private String entityId; 
    
    @Column(name = "action")
    private String action;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(length = 1000, name = "details")
    private String details;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "old_state", columnDefinition = "TEXT")
    private String oldState; 

    @Column(name = "new_state", columnDefinition = "TEXT")
    private String newState;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_type")
    private LogType logType;

     @Column(name = "review_employe")
    private Boolean reviewEmploye;

    @Column(name="review_chef")
    private Boolean reviewChef;

    // Constructors
    public Log() {}

    public Log(LogType logType, String action, String performedBy, String details) {
        this.logType = logType;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

    public Boolean getReviewEmploye() {
        return reviewEmploye;
    }

     public void setReviewEmploye(Boolean reviewEmploye) {
         this.reviewEmploye = reviewEmploye;
     }
    public Boolean getReviewChef() {
        return reviewChef;
    }
    public void setReviewChef(Boolean reviewChef) {
        this.reviewChef = reviewChef;
    }

    
    // Getters and Setters

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

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", logType=" + logType +
                ", entityName='" + entityName + '\'' +
                ", entityId='" + entityId + '\'' +
                ", action='" + action + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                '}';
    }
}