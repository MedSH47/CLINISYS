// src/main/java/com/csys/template/web/rest/ressource/MonitoringResource.java
package com.csys.template.web.rest.ressource;

import java.util.List;
import java.util.Map; // Assurez-vous d'avoir cet import

import org.slf4j.Logger; // Assurez-vous d'avoir cet import
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.service.MonitoringService;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringResource {
    private final Logger log = LoggerFactory.getLogger(MonitoringResource.class); // Assurez-vous d'avoir le logger

    private final MonitoringService monitoringService;

    public MonitoringResource(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/activity-by-hour")
    public ResponseEntity<List<Map<String, Object>>> getHourlyActivity() {
        log.debug("REST request to get hourly activity"); // Ajout du log debug
        List<Map<String, Object>> data = monitoringService.getHourlyActivityCount();
        return ResponseEntity.ok(data);
    }
}