package com.csys.template.log.ressource;

import com.csys.template.log.demain.Log;
import com.csys.template.log.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @GetMapping("/recent")
    public List<Log> getRecentLogs() {
        return logRepository.findTop10ByOrderByTimestampDesc();
    }

    @GetMapping("/entity/{entityName}/{entityId}")
    public List<Log> getEntityHistory(@PathVariable String entityName,
                                     @PathVariable String entityId) {
        return logRepository.findByEntity(entityName, entityId);
    }

    @GetMapping("/audit")
    public List<Log> getAuditLogs() {
        return logRepository.findByLogType(Log.LogType.AUDIT);
    }

    @GetMapping("/notifications")
    public List<Log> getNotificationLogs() {
        return logRepository.findByLogType(Log.LogType.NOTIFICATION);
    }
}