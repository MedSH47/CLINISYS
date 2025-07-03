package com.csys.template.web.rest.ressource;

import com.csys.template.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Endpoint pour les statistiques globales sur une période.
     * ex: /api/dashboard/stats?period=month
     * ex: /api/dashboard/stats?period=custom&startDate=2025-01-01&endDate=2025-01-31
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getGlobalStats(
            @RequestParam(defaultValue = "all") String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        Map<String, Long> stats = dashboardService.getGlobalStatsByPeriod(period, startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    /**
     * Endpoint pour les tickets groupés par statut sur une période.
     */
    @GetMapping("/tickets-by-status")
    public ResponseEntity<List<Map<String, Object>>> getTicketsByStatus(
            @RequestParam(defaultValue = "all") String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
                
        List<Map<String, Object>> stats = dashboardService.getTicketsByStatus(period, startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    /**
     * Endpoint pour les données d'activité sur une période.
     */
    @GetMapping("/live-feeds")
    public ResponseEntity<List<Map<String, Object>>> getLiveFeedsData(
            @RequestParam(defaultValue = "day") String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
                
        List<Map<String, Object>> data = dashboardService.getLiveFeedsData(period, startDate, endDate);
        return ResponseEntity.ok(data);
    }
}