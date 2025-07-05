package com.csys.template.web.rest.ressource;

import com.csys.template.dtoProjection.ClientActivityDTO;
import com.csys.template.dtoProjection.InProgressTicketDTO;
import com.csys.template.dtoProjection.ModuleActivityDTO;
import com.csys.template.dtoProjection.PerformanceStatsDTO;
import com.csys.template.dtoProjection.TeamPerformanceDTO;
import com.csys.template.dtoProjection.UserPerformanceDTO;
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
     * Endpoint pour les statistiques globales sur une période. ex:
     * /api/dashboard/stats?period=month ex:
     * /api/dashboard/stats?period=custom&startDate=2025-01-01&endDate=2025-01-31
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

    @GetMapping("/tickets-by-status-over-time")
    public ResponseEntity<List<Map<String, Object>>> getTicketsByStatusOverTime(
            @RequestParam(defaultValue = "week") String period) {

        // C'est ici que l'erreur se produit
        List<Map<String, Object>> data = dashboardService.getTicketsByStatusOverTime(period);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/global-tickets-by-status")
    public ResponseEntity<List<Map<String, Object>>> getGlobalTicketsByStatus() {
        List<Map<String, Object>> stats = dashboardService.getGlobalTicketsByStatus();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/tickets-by-priority-over-time")
    public ResponseEntity<List<Map<String, Object>>> getTicketsByPriorityOverTime(
            @RequestParam(defaultValue = "last7days") String period) {

        List<Map<String, Object>> data = dashboardService.getTicketsByPriorityOverTime(period);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/in-progress-tickets-gantt")
    public ResponseEntity<List<InProgressTicketDTO>> getInProgressTicketsForGantt(
            @RequestParam(defaultValue = "thismonthweeks") String period) {
        List<InProgressTicketDTO> data = dashboardService.getInProgressTicketsForGantt(period);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/performance-stats")
    public ResponseEntity<List<PerformanceStatsDTO>> getPerformanceStats(
            @RequestParam(defaultValue = "utilisateur") String groupBy,
            @RequestParam(defaultValue = "thismonth") String period) {

        List<PerformanceStatsDTO> data = dashboardService.getPerformanceStats(groupBy, period);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/team-performance")
    public ResponseEntity<List<TeamPerformanceDTO>> getTeamPerformanceStats(
            @RequestParam(defaultValue = "thismonth") String period) {
        List<TeamPerformanceDTO> data = dashboardService.getTeamPerformanceStats(period);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user-performance")
    public ResponseEntity<List<UserPerformanceDTO>> getUserPerformanceStats(
            @RequestParam(defaultValue = "thismonth") String period) {
        List<UserPerformanceDTO> data = dashboardService.getUserPerformanceStats(period);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/client-activity")
    public ResponseEntity<List<ClientActivityDTO>> getClientActivity(
            @RequestParam(defaultValue = "thismonth") String period) {
        List<ClientActivityDTO> data = dashboardService.getClientActivity(period);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/module-activity")
    public ResponseEntity<List<ModuleActivityDTO>> getModuleActivity(
            @RequestParam(defaultValue = "thismonth") String period) {
        List<ModuleActivityDTO> data = dashboardService.getModuleActivity(period);
        return ResponseEntity.ok(data);
    }
}
