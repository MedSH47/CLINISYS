package com.csys.template.config.jpa.audit.log.listener;


import com.csys.template.config.jpa.audit.log.demain.Log;
import com.csys.template.config.jpa.audit.log.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EntityLogger implements ApplicationContextAware {

    private static ApplicationContext context;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EntityLogger.context = applicationContext;
    }

    private LogRepository getLogRepository() {
        return context.getBean(LogRepository.class);
    }

    private void logEntity(String action, Object entity, Long id) {
        try {
            Log log = new Log();
            log.setEntityName(entity.getClass().getSimpleName());
            log.setAction(action);
            log.setPerformedBy(getCurrentUsername()); // Replace with user from context if available
            log.setTimestamp(LocalDateTime.now());
            log.setIpAddress("LOCALHOST"); // Replace with actual request IP if available
            log.setId(id);

            if (action.equals("CREATE") || action.equals("UPDATE")) {
                log.setNewState(objectMapper.writeValueAsString(entity));
            }
            if (action.equals("DELETE") || action.equals("UPDATE")) {
                log.setOldState(objectMapper.writeValueAsString(entity));
            }

            log.setOperationType(action);
            log.setDetails("Audit log for " + action + " on " + entity.getClass().getSimpleName());

            getLogRepository().save(log);
        } catch (Exception e) {
            System.err.println("Failed to log entity: " + e.getMessage());
        }
    }

    @PostPersist
    public void postPersist(Object entity) {
        logEntity("CREATE", entity, getEntityId(entity));
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        logEntity("UPDATE", entity, getEntityId(entity));
    }

    @PostRemove
    public void postRemove(Object entity) {
        logEntity("DELETE", entity, getEntityId(entity));
    }

    private Long getEntityId(Object entity) {
        try {
            return (Long) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }
     private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication() != null ?
                SecurityContextHolder.getContext().getAuthentication().getName() :
                "SYSTEM";
    }
    
}
