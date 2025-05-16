package com.csys.template.config.jpa.audit.log.ressource;

import com.csys.template.config.jpa.audit.log.demain.Log;
import com.csys.template.config.jpa.audit.log.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}