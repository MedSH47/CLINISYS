package com.csys.template.service;

import com.csys.template.domain.*;
import com.csys.template.domain.enum_identifier.Status;
import com.querydsl.core.Tuple; // NOUVEL IMPORT
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList; // NOUVEL IMPORT
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // NOUVEL IMPORT

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);
    private final JPAQueryFactory queryFactory;

    public DashboardService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * Récupère les statistiques globales filtrées par une période de temps.
     */
    public Map<String, Long> getGlobalStatsByPeriod(String period, String startDate, String endDate) {
        log.debug("Request to get global stats for period: {}", period);
        DateRange dateRange = calculateDateRange(period, startDate, endDate);

        QTicket ticket = QTicket.ticket;
        QClient client = QClient.client;

        long newTickets = queryFactory.selectFrom(ticket)
                .where(ticket.dateCreation.between(dateRange.start, dateRange.end))
                .fetchCount();

        long completedTickets = queryFactory.selectFrom(ticket)
                .where(ticket.statue.eq(Status.Termine)
                        .and(ticket.dateCloture.between(dateRange.start, dateRange.end)))
                .fetchCount();
        
        long newClients = queryFactory.selectFrom(client)
                .where(client.dateCreation.between(dateRange.start, dateRange.end))
                .fetchCount();
        
        long totalTickets = queryFactory.selectFrom(ticket).fetchCount();
        long totalClients = queryFactory.selectFrom(client).fetchCount();

        Map<String, Long> stats = new HashMap<>();
        stats.put("newTickets", newTickets);
        stats.put("completedTickets", completedTickets);
        stats.put("newClients", newClients);
        stats.put("totalTickets", totalTickets);
        stats.put("totalClients", totalClients);

        return stats;
    }

    /**
     * CORRIGÉ : Récupère les tickets par statut et transforme le résultat en une liste de maps.
     */
   // In template/service/DashboardService.java

public List<Map<String, Object>> getTicketsByStatus(String period, String startDate, String endDate) {
    log.debug("Request to get tickets by status for period: {}", period);
    DateRange dateRange = calculateDateRange(period, startDate, endDate);
    QTicket ticket = QTicket.ticket;

    List<Tuple> results = queryFactory
            .select(ticket.statue, ticket.id.count())
            .from(ticket)
            .where(ticket.dateCreation.between(dateRange.start, dateRange.end))
            .groupBy(ticket.statue)
            .fetch();
    
    // ADD THIS LINE TO DEBUG
    log.info("Tickets by status query results: {}", results);

    return results.stream()
            .map(tuple -> {
                Map<String, Object> map = new HashMap<>();
                map.put("status", tuple.get(ticket.statue));
                map.put("count", tuple.get(ticket.id.count()));
                return map;
            })
            .collect(Collectors.toList());
}
    
    /**
     * CORRIGÉ : Récupère les données d'activité et transforme le résultat.
     */
    public List<Map<String, Object>> getLiveFeedsData(String period, String startDate, String endDate) {
        log.debug("Request to get live feeds data for period: {}", period);
        DateRange dateRange = calculateDateRange(period, startDate, endDate);
        QTicket ticket = QTicket.ticket;
        
        List<Tuple> results = queryFactory
            .select(ticket.dateCreation.dayOfMonth(), ticket.id.count())
            .from(ticket)
            .where(ticket.dateCreation.between(dateRange.start, dateRange.end))
            .groupBy(ticket.dateCreation.dayOfMonth())
            .orderBy(ticket.dateCreation.dayOfMonth().asc())
            .fetch();

        return results.stream()
                .map(tuple -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", tuple.get(ticket.dateCreation.dayOfMonth())); // Exemple: "jour du mois"
                    map.put("ticketCount", tuple.get(ticket.id.count()));
                    return map;
                })
                .collect(Collectors.toList());
    }


    /**
     * Méthode utilitaire pour calculer les dates de début et de fin.
     */
    private DateRange calculateDateRange(String period, String start, String end) {
        LocalDateTime startDate;
        LocalDateTime endDate = LocalDateTime.now();

        switch (period.toLowerCase()) {
            case "day":
                startDate = LocalDate.now().atStartOfDay();
                break;
            case "week":
                startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
                break;
            case "month":
                startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
                break;
            case "year":
                startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
                break;
            case "custom":
                startDate = LocalDate.parse(start).atStartOfDay();
                endDate = LocalDate.parse(end).atTime(LocalTime.MAX);
                break;
            case "all":
            default:
                startDate = LocalDateTime.of(2000, 1, 1, 0, 0); 
                break;
        }
        return new DateRange(startDate, endDate);
    }

    /**
     * Classe interne pour stocker une plage de dates.
     */
    private static class DateRange {
        final LocalDateTime start;
        final LocalDateTime end;

        DateRange(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }
    }
}