package com.csys.template.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle; // NOUVEL IMPORT
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csys.template.domain.QClient;
import com.csys.template.domain.QTicket;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoProjection.ClientActivityDTO;
import com.csys.template.dtoProjection.InProgressTicketDTO;
import com.csys.template.dtoProjection.ModuleActivityDTO;
import com.csys.template.dtoProjection.PerformanceStatsDTO;
import com.csys.template.dtoProjection.TeamPerformanceDTO;
import com.csys.template.dtoProjection.UserPerformanceDTO;
import com.querydsl.core.Tuple; // NOUVEL IMPORT
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression; // NOUVEL IMPORT
import com.querydsl.jpa.impl.JPAQueryFactory;

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
     * CORRIGÉ : Récupère les tickets par statut et transforme le résultat en
     * une liste de maps.
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

    // Dans src/main/java/com/csys/template/service/DashboardService.java
    // Dans src/main/java/com/csys/template/service/DashboardService.java
    public List<Map<String, Object>> getTicketsByStatusOverTime(String period) {
        log.debug("Request for time series ticket data for period: {}", period);

        QTicket ticket = QTicket.ticket;
        NumberExpression<Integer> timeGroupExpression;
        BooleanExpression dateFilter;
        Map<Integer, String> timeLabels = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        switch (period.toLowerCase()) {
            case "byyear":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(year, {0})", ticket.dateCreation);
                dateFilter = ticket.dateCreation.year().between(2022, 2025); // >= 2022
                break;

            case "thisyearmonths":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(month, {0})", ticket.dateCreation);
                dateFilter = ticket.dateCreation.year().eq(now.getYear());
                break;

            case "thismonthweeks":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(week, {0})", ticket.dateCreation);
                dateFilter = ticket.dateCreation.year().eq(now.getYear())
                        .and(ticket.dateCreation.month().eq(now.getMonthValue()));
                break;

            case "last7days":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(weekday, {0})", ticket.dateCreation);
                dateFilter = ticket.dateCreation.after(now.minusDays(7));
                timeLabels.put(1, "Dim");
                timeLabels.put(2, "Lun");
                timeLabels.put(3, "Mar");
                timeLabels.put(4, "Mer");
                timeLabels.put(5, "Jeu");
                timeLabels.put(6, "Ven");
                timeLabels.put(7, "Sam");
                break;

            default: // "today"
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(hour, {0})", ticket.dateCreation);
                // VERSION CORRIGÉE : On utilise explicitement DATEPART(dayofyear, ...)
                dateFilter = Expressions.numberTemplate(Integer.class, "DATEPART(dayofyear, {0})", ticket.dateCreation)
                        .eq(now.getDayOfYear())
                        .and(ticket.dateCreation.year().eq(now.getYear()));
                break;
        }

        List<Tuple> results = queryFactory
                .select(timeGroupExpression, ticket.statue, ticket.id.count())
                .from(ticket)
                .where(dateFilter)
                .groupBy(timeGroupExpression, ticket.statue)
                .orderBy(timeGroupExpression.asc())
                .fetch();

        Map<String, Map<String, Object>> pivotData = new LinkedHashMap<>();

        for (Tuple row : results) {
            Integer timeGroupValue = row.get(timeGroupExpression);
            Status status = row.get(ticket.statue);
            Long count = row.get(ticket.id.count());

            if (timeGroupValue == null || status == null) {
                continue;
            }

            String timeUnitLabel = "";
            switch (period.toLowerCase()) {
                case "byyear":
                    timeUnitLabel = String.valueOf(timeGroupValue);
                    break;
                case "thisyearmonths":
                    timeUnitLabel = Month.of(timeGroupValue).getDisplayName(TextStyle.SHORT, Locale.FRENCH);
                    break;
                case "thismonthweeks":
                    timeUnitLabel = "Sem " + timeGroupValue;
                    break;
                case "last7days":
                    timeUnitLabel = timeLabels.getOrDefault(timeGroupValue, "?");
                    break;
                default: // "today"
                    timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(hour, {0})", ticket.dateCreation);
                    // VERSION CORRIGÉE : On utilise DATEPART(dayofyear, ...) que SQL Server comprend
                    dateFilter = Expressions.numberTemplate(Integer.class, "DATEPART(dayofyear, {0})", ticket.dateCreation)
                            .eq(now.getDayOfYear())
                            .and(ticket.dateCreation.year().eq(now.getYear()));
                    break;
            }

            Map<String, Object> timeUnitMap = pivotData.computeIfAbsent(timeUnitLabel, k -> new LinkedHashMap<>());
            timeUnitMap.put("time_unit", timeUnitLabel);
            timeUnitMap.put(status.name().toLowerCase(), count);
        }

        return new ArrayList<>(pivotData.values());
    }

    // Dans src/main/java/com/csys/template/service/DashboardService.java
    public List<Map<String, Object>> getGlobalTicketsByStatus() {
        log.debug("Request for GLOBAL tickets by status");
        QTicket ticket = QTicket.ticket;

        // Requête simple sans filtre de date
        List<Tuple> results = queryFactory
                .select(ticket.statue, ticket.id.count())
                .from(ticket)
                .groupBy(ticket.statue)
                .fetch();

        // Transformation du résultat comme avant
        return results.stream()
                .map(tuple -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", tuple.get(ticket.statue));
                    map.put("count", tuple.get(ticket.id.count()));
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Dans src/main/java/com/csys/template/service/DashboardService.java
    public List<Map<String, Object>> getTicketsByPriorityOverTime(String period) {
        log.debug("Request for time series ticket data by PRIORITY for period: {}", period);

        QTicket ticket = QTicket.ticket;
        NumberExpression<Integer> timeGroupExpression = null;
        BooleanExpression dateFilter = null;
        Map<Integer, String> timeLabels = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        // La logique de période est correcte
        switch (period.toLowerCase()) {
            case "byyear":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(year, {0})", ticket.dateCreation);
                dateFilter = ticket.dateCreation.year().between(2022, 2025);
                break;
            case "thisyearmonths":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(month, {0})", ticket.dateCreation);
                dateFilter = ticket.dateCreation.year().eq(now.getYear());
                break;
            case "thismonthweeks":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(week, {0})", ticket.dateCreation);
                dateFilter = ticket.dateCreation.year().eq(now.getYear()).and(ticket.dateCreation.month().eq(now.getMonthValue()));
                break;
            case "last7days":
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(weekday, {0})", ticket.dateCreation);
                LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
                LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59);
                dateFilter = ticket.dateCreation.between(startOfWeek, endOfWeek);
                timeLabels.put(1, "Dim");
                timeLabels.put(2, "Lun");
                timeLabels.put(3, "Mar");
                timeLabels.put(4, "Mer");
                timeLabels.put(5, "Jeu");
                timeLabels.put(6, "Ven");
                timeLabels.put(7, "Sam");
                break;
            default: // "today"
                timeGroupExpression = Expressions.numberTemplate(Integer.class, "DATEPART(hour, {0})", ticket.dateCreation);
                dateFilter = Expressions.numberTemplate(Integer.class, "DATEPART(dayofyear, {0})", ticket.dateCreation).eq(now.getDayOfYear()).and(ticket.dateCreation.year().eq(now.getYear()));
                break;
        }

        List<Tuple> results = queryFactory
                .select(timeGroupExpression, ticket.priorite, ticket.id.count())
                .from(ticket)
                .where(dateFilter)
                .groupBy(timeGroupExpression, ticket.priorite)
                .orderBy(timeGroupExpression.asc())
                .fetch();

        Map<String, Map<String, Object>> pivotData = new LinkedHashMap<>();

        for (Tuple row : results) {
            Integer timeGroupValue = row.get(timeGroupExpression);
            Priorite priorite = row.get(ticket.priorite);
            Long count = row.get(ticket.id.count());

            if (timeGroupValue == null || priorite == null) {
                continue;
            }

            // --- DÉBUT DE LA CORRECTION ---
            // Ce bloc de code était manquant. Il génère les libellés pour l'axe X.
            String timeUnitLabel = "";
            switch (period.toLowerCase()) {
                case "byyear":
                    timeUnitLabel = String.valueOf(timeGroupValue);
                    break;
                case "thisyearmonths":
                    timeUnitLabel = Month.of(timeGroupValue).getDisplayName(TextStyle.SHORT, Locale.FRENCH);
                    break;
                case "thismonthweeks":
                    timeUnitLabel = "Sem " + timeGroupValue;
                    break;
                case "last7days":
                    timeUnitLabel = timeLabels.getOrDefault(timeGroupValue, "?");
                    break;
                default: // "today"
                    timeUnitLabel = String.format("%02d:00", timeGroupValue);
                    break;
            }
            // --- FIN DE LA CORRECTION ---

            Map<String, Object> timeUnitMap = pivotData.computeIfAbsent(timeUnitLabel, k -> new LinkedHashMap<>());
            timeUnitMap.put("time_unit", timeUnitLabel);
            timeUnitMap.put(priorite.name().toLowerCase(), count);
        }

        return new ArrayList<>(pivotData.values());
    }

    // Dans src/main/java/com/csys/template/service/DashboardService.java
    public List<InProgressTicketDTO> getInProgressTicketsForGantt(String period) {
        log.debug("Request for in-progress tickets for Gantt chart for period: {}", period);

        QTicket ticket = QTicket.ticket;
        BooleanExpression dateFilter;
        LocalDateTime now = LocalDateTime.now();

        // La logique de période ne change pas
        switch (period.toLowerCase()) {
            case "byyear":
                dateFilter = ticket.dateCreation.year().goe(2022);
                break;
            case "thisyearmonths":
                dateFilter = ticket.dateCreation.year().eq(now.getYear());
                break;
            case "thismonthweeks":
                dateFilter = ticket.dateCreation.year().eq(now.getYear()).and(ticket.dateCreation.month().eq(now.getMonthValue()));
                break;
            case "last7days":
                LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
                dateFilter = ticket.dateCreation.goe(startOfWeek);
                break;
            default: // "today"
                LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
                LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59, 59);
                dateFilter = ticket.dateCreation.between(startOfDay, endOfDay);
                break;
        }

        return queryFactory
                .select(ticket.titre, ticket.debutTraitement, ticket.date_echeance)
                .from(ticket)
                .where(ticket.statue.eq(Status.En_cours).and(dateFilter))
                .orderBy(ticket.date_echeance.asc())
                .fetch()
                .stream()
                .map(tuple -> new InProgressTicketDTO(
                // CORRECTION : Le titre du ticket est maintenant passé au paramètre "name" du DTO
                tuple.get(ticket.titre),
                tuple.get(ticket.debutTraitement),
                tuple.get(ticket.date_echeance)
        ))
                .collect(Collectors.toList());
    }

    public List<PerformanceStatsDTO> getPerformanceStats(String groupBy, String period) {
        log.debug("Request for performance stats grouped by {} for period {}", groupBy, period);

        QTicket ticket = QTicket.ticket;
        BooleanExpression dateFilter;

        // --- DÉBUT DE LA NOUVELLE LOGIQUE DE PÉRIODE ---
        LocalDateTime now = LocalDateTime.now();
        switch (period.toLowerCase()) {
            case "last7days":
                dateFilter = ticket.dateCloture.after(now.minusDays(7));
                break;
            default: // "thismonth"
                dateFilter = ticket.dateCloture.year().eq(now.getYear())
                        .and(ticket.dateCloture.month().eq(now.getMonthValue()));
                break;
        }

        // --- NOUVELLE LOGIQUE POUR L'AXE DU TEMPS (X-axis) ---
        // On formate la date directement en SQL pour un affichage propre (ex: "05 Juil")
        StringExpression timeGroupExpression = Expressions.stringTemplate("FORMAT({0}, 'dd MMM', 'fr-FR')", ticket.dateCloture);

        // Définition du groupement (Utilisateur, Module...) - Inchangé
        SimpleExpression<String> groupPathExpression;
        switch (groupBy.toLowerCase()) {
            case "module":
                groupPathExpression = ticket.module.designation;
                break;
            case "equipe":
                groupPathExpression = ticket.module.equipe.designation;
                break;
            default: // "utilisateur"
                groupPathExpression = ticket.idUtilisateur.nom;
                break;
        }

        // Requête finale
        List<Tuple> results = queryFactory
                .select(
                        timeGroupExpression,
                        groupPathExpression,
                        ticket.id.count(),
                        new CaseBuilder().when(ticket.dateCloture.loe(ticket.date_echeance)).then(1L).otherwise(0L).sum()
                )
                .from(ticket)
                .join(ticket.idUtilisateur)
                .join(ticket.module)
                .where(
                        ticket.statue.eq(Status.Termine)
                                .and(dateFilter)
                                .and(ticket.dateCloture.isNotNull())
                                .and(ticket.date_echeance.isNotNull())
                )
                .groupBy(timeGroupExpression, groupPathExpression)
                .orderBy(timeGroupExpression.asc())
                .fetch();

        // Transformation en DTOs
        return results.stream().map(tuple -> new PerformanceStatsDTO(
                tuple.get(timeGroupExpression), // L'unité de temps est maintenant un String formaté
                tuple.get(groupPathExpression),
                tuple.get(ticket.id.count()),
                tuple.get(3, Long.class)
        )).collect(Collectors.toList());
    }

    public List<TeamPerformanceDTO> getTeamPerformanceStats(String period) {
        log.debug("Request for team performance stats for period {}", period);

        QTicket ticket = QTicket.ticket;
        BooleanExpression dateFilter;

        LocalDateTime now = LocalDateTime.now();
        // --- DÉBUT DE LA LOGIQUE DE PÉRIODE AMÉLIORÉE ---
        switch (period.toLowerCase()) {
            case "thisyearmonths":
                dateFilter = ticket.dateCloture.year().eq(now.getYear());
                break;
            case "byyear":
                dateFilter = ticket.dateCloture.year().between(now.getYear() - 3, now.getYear()); // 3 dernières années
                break;
            case "last7days":
                dateFilter = ticket.dateCloture.after(now.minusDays(7));
                break;
            default: // "thismonth"
                dateFilter = ticket.dateCloture.year().eq(now.getYear())
                        .and(ticket.dateCloture.month().eq(now.getMonthValue()));
                break;
        }
        // --- FIN DE LA LOGIQUE DE PÉRIODE ---

        List<Tuple> results = queryFactory
                .select(
                        ticket.module.equipe.designation,
                        ticket.id.count(),
                        new CaseBuilder().when(ticket.dateCloture.loe(ticket.date_echeance)).then(1L).otherwise(0L).sum()
                )
                .from(ticket)
                .join(ticket.module.equipe)
                .where(
                        ticket.statue.eq(Status.Termine)
                                .and(dateFilter)
                                .and(ticket.dateCloture.isNotNull())
                                .and(ticket.date_echeance.isNotNull())
                )
                .groupBy(ticket.module.equipe.designation)
                .fetch();

        return results.stream().map(tuple -> {
            String teamName = tuple.get(ticket.module.equipe.designation);
            long totalTickets = tuple.get(1, Long.class);
            long onTimeTickets = tuple.get(2, Long.class);
            double onTimeRate = (totalTickets > 0) ? ((double) onTimeTickets / totalTickets) * 100.0 : 0.0;
            return new TeamPerformanceDTO(teamName, onTimeRate);
        }).collect(Collectors.toList());
    }

    public List<UserPerformanceDTO> getUserPerformanceStats(String period) {
        log.debug("Request for user performance stats for period {}", period);

        QTicket ticket = QTicket.ticket;
        BooleanExpression dateFilter;

        LocalDateTime now = LocalDateTime.now();
        // --- DÉBUT DE LA LOGIQUE DE PÉRIODE AMÉLIORÉE ---
        switch (period.toLowerCase()) {
            case "thisyearmonths":
                dateFilter = ticket.dateCloture.year().eq(now.getYear());
                break;
            case "byyear":
                dateFilter = ticket.dateCloture.year().between(now.getYear() - 3, now.getYear()); // 3 dernières années
                break;
            case "last7days":
                dateFilter = ticket.dateCloture.after(now.minusDays(7));
                break;
            default: // "thismonth"
                dateFilter = ticket.dateCloture.year().eq(now.getYear())
                        .and(ticket.dateCloture.month().eq(now.getMonthValue()));
                break;
        }
        // --- FIN DE LA LOGIQUE DE PÉRIODE ---

        List<Tuple> results = queryFactory
                .select(
                        ticket.idUtilisateur.prenom.concat(" ").concat(ticket.idUtilisateur.nom),
                        ticket.id.count(),
                        new CaseBuilder().when(ticket.dateCloture.loe(ticket.date_echeance)).then(1L).otherwise(0L).sum()
                )
                .from(ticket)
                .join(ticket.idUtilisateur)
                .where(
                        ticket.statue.eq(Status.Termine)
                                .and(dateFilter)
                                .and(ticket.dateCloture.isNotNull())
                                .and(ticket.date_echeance.isNotNull())
                )
                .groupBy(ticket.idUtilisateur.prenom, ticket.idUtilisateur.nom)
                .fetch();

        return results.stream().map(tuple -> {
            String userName = tuple.get(0, String.class);
            long totalTickets = tuple.get(1, Long.class);
            long onTimeTickets = tuple.get(2, Long.class);
            double onTimeRate = (totalTickets > 0) ? ((double) onTimeTickets / totalTickets) * 100.0 : 0.0;
            return new UserPerformanceDTO(userName, onTimeRate, totalTickets);
        }).collect(Collectors.toList());
    }

    public List<ClientActivityDTO> getClientActivity(String period) {
        log.debug("Request for client activity for period {}", period);

        QTicket ticket = QTicket.ticket;
        BooleanExpression dateFilter = null; // Initialisé à null par défaut

        LocalDateTime now = LocalDateTime.now();
        // --- Logique de période améliorée ---
        switch (period.toLowerCase()) {
            case "thisyear":
                dateFilter = ticket.dateCreation.year().eq(now.getYear());
                break;
            case "lastyear": // NOUVELLE PÉRIODE
                dateFilter = ticket.dateCreation.year().eq(now.getYear() - 1);
                break;
            case "last90days":
                dateFilter = ticket.dateCreation.after(now.minusDays(90));
                break;
            case "alltime": // NOUVELLE PÉRIODE
                dateFilter = null; // Pas de filtre de date pour tout l'historique
                break;
            default: // "thismonth"
                dateFilter = ticket.dateCreation.year().eq(now.getYear())
                        .and(ticket.dateCreation.month().eq(now.getMonthValue()));
                break;
        }

        // On construit la requête de base
        var query = queryFactory
                .select(
                        ticket.idClient.nomComplet,
                        ticket.id.count(),
                        new CaseBuilder().when(ticket.statue.notIn(Status.Termine, Status.Refuse)).then(1L).otherwise(0L).sum()
                )
                .from(ticket)
                .join(ticket.idClient);

        // On ajoute le filtre de date seulement s'il n'est pas null
        if (dateFilter != null) {
            query.where(dateFilter);
        }

        // On finalise et exécute la requête
        List<Tuple> results = query
                .groupBy(ticket.idClient.nomComplet)
                .orderBy(ticket.id.count().desc())
                .fetch();

        return results.stream().map(tuple -> new ClientActivityDTO(
                tuple.get(ticket.idClient.nomComplet),
                tuple.get(1, Long.class),
                tuple.get(2, Long.class)
        )).collect(Collectors.toList());
    }

    public List<ModuleActivityDTO> getModuleActivity(String period) {
        log.debug("Request for module activity for period {}", period);

        QTicket ticket = QTicket.ticket;
        BooleanExpression dateFilter = null;
        LocalDateTime now = LocalDateTime.now();

        switch (period.toLowerCase()) {
            case "thisyear":
                dateFilter = ticket.dateCreation.year().eq(now.getYear());
                break;
            case "lastyear":
                dateFilter = ticket.dateCreation.year().eq(now.getYear() - 1);
                break;
            case "last90days":
                dateFilter = ticket.dateCreation.after(now.minusDays(90));
                break;
            case "alltime":
                dateFilter = null;
                break;
            default: // "thismonth"
                dateFilter = ticket.dateCreation.year().eq(now.getYear())
                        .and(ticket.dateCreation.month().eq(now.getMonthValue()));
                break;
        }

        var query = queryFactory
                .select(
                        ticket.module.designation,
                        ticket.id.count(),
                        new CaseBuilder().when(ticket.statue.notIn(Status.Termine, Status.Refuse)).then(1L).otherwise(0L).sum()
                )
                .from(ticket)
                .join(ticket.module); // Jointure pour accéder au nom du module

        if (dateFilter != null) {
            query.where(dateFilter);
        }

        List<Tuple> results = query
                .groupBy(ticket.module.designation)
                .orderBy(ticket.id.count().desc())
                .fetch();

        return results.stream().map(tuple -> new ModuleActivityDTO(
                tuple.get(ticket.module.designation),
                tuple.get(1, Long.class),
                tuple.get(2, Long.class)
        )).collect(Collectors.toList());
    }
}
