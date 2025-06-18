package com.csys.template.log.service;

import com.csys.template.domain.Ticket;
import com.csys.template.log.demain.Log;
import com.csys.template.log.repository.LogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public LogService(LogRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    public void logAudit(String action, Object entity) {
        try {
            Log log = new Log(Log.LogType.AUDIT, action, "SYSTEM", "Audit log for " + action + " on " + entity.getClass().getSimpleName());
            log.setEntityName(entity.getClass().getSimpleName());
            if (action.equals("CREATE") || action.equals("UPDATE")) {
                log.setNewState(objectMapper.writeValueAsString(entity));
            }
            if (action.equals("DELETE") || action.equals("UPDATE")) {
                log.setOldState(objectMapper.writeValueAsString(entity));
            }
            logRepository.save(log);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void logNotification(String recipient, String subject, String body) {
        String details = "Recipient: " + recipient + ", Subject: " + subject;
        Log log = new Log(Log.LogType.NOTIFICATION, "EMAIL_SENT", "SYSTEM", details);
        logRepository.save(log);
    }
    public void logTicketReview(Ticket ticket, Boolean reviewEmploye,Boolean reviewChef) {
        Log log = new Log(Log.LogType.AUDIT, "NOTIFICATION", "SYSTEM", "Ticket review for employee updated");
        log.setEntityName("Ticket");
        log.setEntityId(ticket.getId().toString());
        log.setReviewEmploye(reviewEmploye);
        log.setReviewChef(reviewChef);
        logRepository.save(log);
        
    }
   
    
}