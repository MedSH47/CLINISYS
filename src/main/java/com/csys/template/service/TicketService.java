package com.csys.template.service;

// ... (tous les imports existants, assurez-vous qu'ils sont tous là) ...
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.csys.template.AI_Search_Box.AiQueryResponse;
import com.csys.template.AI_Search_Box.TicketSpecification;
import com.csys.template.domain.Module;
import com.csys.template.domain.QTicket;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.factory.TicketFactory;
import com.csys.template.log.service.LogService;
import com.csys.template.repository.ModuleRepository;
import com.csys.template.repository.TicketRepository;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.util.WhereClauseBuilder;

@Service
@Transactional
public class TicketService {

    private final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final LogService logService;
    private final RestTemplate restTemplate;
    private final ModuleRepository moduleRepository;
    private final UtilisateurRepository utilisateurRepository;

    public TicketService(TicketRepository ticketRepository, LogService logService, RestTemplate restTemplate,
            ModuleRepository moduleRepository, UtilisateurRepository utilisateurRepository) {
        this.ticketRepository = ticketRepository;
        this.logService = logService;
        this.restTemplate = restTemplate;
        this.moduleRepository = moduleRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public TicketResponseDTO save(TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to save Ticket: {}", ticketRequestDTO);
        Ticket ticket = TicketFactory.toEntity(ticketRequestDTO);
        ticket = ticketRepository.save(ticket);
        return TicketFactory.toResponseDTO(ticket);
    }

    public TicketResponseDTO update(Integer ticketId, TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to update Ticket: {}", ticketId);
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
        if (ticketRequestDTO.getIdModule() != null && existingTicket.getModule() == null) {
            logService.logTicketReview(existingTicket, false, true);

            Module assignedModule = moduleRepository.findById(ticketRequestDTO.getIdModule())
                    .orElseThrow(() -> new IllegalArgumentException("module.NotFound"));

            if (assignedModule.getEquipe() != null && assignedModule.getEquipe().getChefEquipe() != null) {
                Utilisateur chefEquipe = assignedModule.getEquipe().getChefEquipe();
                String message = String.format(
                        "Ticket '#%d: %s' has been assigned to the module '%s', managed by your team.",
                        existingTicket.getId(),
                        existingTicket.getTitre(),
                        assignedModule.getDesignation());
            }
        }

        if (ticketRequestDTO.getIdUtilisateur() != null && existingTicket.getIdUtilisateur() == null) {
            logService.logTicketReview(existingTicket, true, true);

            Utilisateur assignedUser = utilisateurRepository.findById(ticketRequestDTO.getIdUtilisateur())
                    .orElseThrow(() -> new IllegalArgumentException("utilisateur.NotFound"));

            String message = String.format(
                    "You have been assigned a new ticket: '#%d: %s'.",
                    existingTicket.getId(),
                    existingTicket.getTitre());
        }

        TicketFactory.updateFromDTO(existingTicket, ticketRequestDTO);

        ticketRepository.save(existingTicket);
        return TicketFactory.toResponseDTO(existingTicket);
    }

    @Transactional(readOnly = true)
    public TicketResponseDTO findOne(Integer id) {
        log.debug("Request to get Ticket: {}", id);
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return TicketFactory.toResponseDTO(ticket);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> findAll(Status statue, Integer idModule, Priorite priorite, Boolean[] actifs) {
        log.debug("Request to get All Tickets with filters");
        QTicket qTicket = QTicket.ticket;
        WhereClauseBuilder builder = new WhereClauseBuilder()
                .optionalAnd(statue, () -> qTicket.statue.eq(statue))
                .optionalAnd(idModule, () -> qTicket.module().id.eq(idModule))
                .optionalAnd(actifs, () -> qTicket.actif.in(actifs))
                .optionalAnd(priorite, () -> qTicket.priorite.eq(priorite));

        List<Ticket> result = (List<Ticket>) ticketRepository.findAll(builder);
        return TicketFactory.toResponseDTOs(result);
    }

    public ResponseEntity<?> delete(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
        if (ticket.getModule() != null) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(java.util.Map.of("message", "ticket " + ticket.getId() + " cannot Delete With Module"));
        }
        if (ticket.getIdUtilisateur() != null) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(java.util.Map.of("message", "ticket " + ticket.getId() + " cannot Delete With User"));
        }
        TicketResponseDTO ticketResponseDTO = TicketFactory.toResponseDTO(ticket);
        if (!ticketResponseDTO.getChildTickets().isEmpty()) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(java.util.Map.of("message",
                            "ticket " + ticketResponseDTO.getId() + " cannot Delete With Child Tickets"));
        }
        log.debug("Request to delete Ticket: {}", id);
        ticketRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public List<TicketResponseDTO> findAllParents() {
        List<Ticket> tickets = ticketRepository.findAll();
        return TicketFactory.toResponseDTOsParents(tickets);
    }

    public List<TicketResponseDTO> searchByNaturalLanguage(String query) {
        String aiServiceUrl = "http://localhost:5001/parse-query";
        // CORRECTION : Changer 'singletonSingletonMap' en 'singletonMap'
        Map<String, String> requestBody = Collections.singletonMap("query", query); // <-- Cette ligne doit être
                                                                                    // corrigée
        AiQueryResponse aiResponse = restTemplate.postForObject(aiServiceUrl, requestBody, AiQueryResponse.class);

        if (aiResponse != null && "ticket".equals(aiResponse.getEntityType())) {
            Specification<Ticket> spec = TicketSpecification.findByEntities(aiResponse.getEntities());
            return ticketRepository.findAll(spec).stream()
                    .map(TicketFactory::toResponseDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public Map<Status, Long> getCountsByStatus() {
        log.debug("Request to get ticket counts by status");
        List<Ticket> allTickets = ticketRepository.findAll();
        return allTickets.stream()
                .collect(Collectors.groupingBy(Ticket::getStatue, Collectors.counting()));
    }

    public List<Map<String, Object>> getCalendarEvents() {
        log.debug("Request to get calendar events from tickets"); // Ajout du log pour confirmation
        List<Ticket> tickets = ticketRepository.findAll(); // Peut être optimisé avec des critères de date si trop de
                                                           // tickets
        return tickets.stream()
                .filter(t -> t.getDate_echeance() != null) // Ne prendre que les tickets avec une date d'échéance
                .map(t -> {
                    Map<String, Object> event = new HashMap<>();
                    event.put("id", t.getId());
                    event.put("title", "Échéance Ticket #" + t.getId() + ": " + t.getTitre()); // Titre plus informatif
                    event.put("start", t.getDate_echeance().toString()); // Format ISO 8601 de LocalDateTime

                    // Pour FullCalendar, si c'est une échéance sans durée, start et end peuvent
                    // être les mêmes.
                    // Si vous voulez que l'événement s'étende sur toute la journée, allDay: true et
                    // pas de temps.
                    // Si c'est un événement ponctuel d'une heure, vous pouvez ajouter 1 heure à la
                    // date de fin.
                    event.put("end", t.getDate_echeance().plusHours(1).toString()); // Exemple: événement d'une heure

                    event.put("allDay", false); // Par défaut, non "toute la journée" si une heure est spécifiée

                    // Couleur dynamique basée sur la priorité ou le statut
                    String color = "#ADD8E6"; // Default light blue
                    if (t.getPriorite() == Priorite.Haute) {
                        color = "#FF6347"; // Tomato (reddish)
                    } else if (t.getPriorite() == Priorite.Moyenne) {
                        color = "#FFD700"; // Gold (yellowish)
                    } else if (t.getPriorite() == Priorite.Basse) {
                        color = "#90EE90"; // LightGreen
                    }
                    event.put("color", color);

                    // Vous pouvez aussi ajouter d'autres données pour un clic détaillé sur le
                    // frontend
                    event.put("extendedProps", Map.of(
                            "ticketStatus", t.getStatue().toString(),
                            "ticketPriority", t.getPriorite().toString(),
                            "assignedTo",
                            (t.getIdUtilisateur() != null
                                    ? t.getIdUtilisateur().getPrenom() + " " + t.getIdUtilisateur().getNom()
                                    : "Non assigné")));

                    return event;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getGlobalTicketCounts() {
        log.debug("Request to get global ticket counts");
        List<Ticket> allTickets = ticketRepository.findAll();

        long totalTickets = allTickets.size();
        long ticketsEnAttente = allTickets.stream().filter(t -> t.getStatue() == Status.En_attente).count();
        long ticketsEnCours = allTickets.stream().filter(t -> t.getStatue() == Status.En_cours).count();
        long ticketsAcceptes = allTickets.stream().filter(t -> t.getStatue() == Status.Accepte).count();
        long ticketsRefuses = allTickets.stream().filter(t -> t.getStatue() == Status.Refuse).count();

        LocalDate today = LocalDate.now();
        // LocalDateTime startOfDay = today.atStartOfDay(); // Variable not used
        // LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // Variable not used

        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);

        long ticketsTerminesToday = allTickets.stream()
                .filter(t -> t.getStatue() == Status.Termine && t.getDate_echeance() != null &&
                        t.getDate_echeance().toLocalDate().isEqual(today))
                .count();

        long ticketsTerminesThisWeek = allTickets.stream()
                .filter(t -> t.getStatue() == Status.Termine && t.getDate_echeance() != null &&
                        !t.getDate_echeance().toLocalDate().isBefore(startOfWeek) &&
                        !t.getDate_echeance().toLocalDate().isAfter(today))
                .count();

        Map<String, Long> counts = new HashMap<>();
        counts.put("totalTickets", totalTickets);
        counts.put("ticketsEnAttente", ticketsEnAttente);
        counts.put("ticketsEnCours", ticketsEnCours);
        counts.put("ticketsAcceptes", ticketsAcceptes);
        counts.put("ticketsTerminesToday", ticketsTerminesToday);
        counts.put("ticketsTerminesThisWeek", ticketsTerminesThisWeek);
        counts.put("ticketsRefuses", ticketsRefuses);

        return counts;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getActiveTicketsByAssigneeOrModule(String groupBy) {
        log.debug("Request to get active tickets grouped by: {}", groupBy);

        List<Ticket> activeTickets = ticketRepository.findAll().stream()
                .filter(t -> t.getStatue() != Status.Termine && t.getStatue() != Status.Refuse)
                .collect(Collectors.toList());

        Map<String, Long> groupedCounts = new HashMap<>();

        if ("employee".equalsIgnoreCase(groupBy)) {
            groupedCounts = activeTickets.stream()
                    .filter(t -> t.getIdUtilisateur() != null)
                    .collect(Collectors.groupingBy(
                            t -> t.getIdUtilisateur().getPrenom() + " " + t.getIdUtilisateur().getNom(),
                            Collectors.counting()));
            long nonAssignedCount = activeTickets.stream()
                    .filter(t -> t.getIdUtilisateur() == null)
                    .count();
            if (nonAssignedCount > 0) {
                groupedCounts.put("Non assigné", nonAssignedCount);
            }

        } else if ("module".equalsIgnoreCase(groupBy)) {
            groupedCounts = activeTickets.stream()
                    .filter(t -> t.getModule() != null) // <-- CHANGEMENT ICI : t.getModule() au lieu de t.getIdModule()
                    .collect(Collectors.groupingBy(
                            ticket -> ticket.getModule().getDesignation(), // <-- CHANGEMENT ICI : ticket.getModule()
                            Collectors.counting()));

            long noModuleCount = activeTickets.stream()
                    .filter(t -> t.getModule() == null) // <-- CHANGEMENT ICI : t.getModule()
                    .count();
            if (noModuleCount > 0) {
                groupedCounts.put("Sans module", noModuleCount);
            }

        } else {
            throw new IllegalArgumentException("Invalid groupBy parameter. Must be 'employee' or 'module'.");
        }

        List<Map<String, Object>> result = groupedCounts.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("category", entry.getKey());
                    item.put("activeTickets", entry.getValue());
                    return item;
                })
                .sorted(Comparator.comparing(item -> (Long) item.get("activeTickets"), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return result;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPerformanceStats(String groupBy, String period) {
        log.debug("Request to get performance stats by {} for period: {}", groupBy, period);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate;

        // Définir la période (simplifié pour l'exemple)
        if ("current_month".equalsIgnoreCase(period)) {
            startDate = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        } else if ("last_7_days".equalsIgnoreCase(period)) {
            startDate = now.minusDays(7).with(LocalTime.MIN);
        } else { // Par défaut: mois courant
            startDate = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        }

        // Récupérer les tickets terminés DANS la période
        // IMPORTANT: Pour la date de fin, vous devrez utiliser un champ qui indique
        // quand le ticket a été "terminé".
        // Si vous n'avez que date_echeance, ce n'est pas idéal pour la performance.
        // Je vais filtrer sur dateCreation pour l'exemple, si vous n'avez pas de date
        // de clôture.
        // Idéalement, vous auriez
        // `ticketRepository.findByStatueAndDateClotureAfter(Status.Termine,
        // startDate);`
        List<Ticket> allTickets = ticketRepository.findAll(); // <-- AJOUTEZ CETTE LIGNE

        List<Ticket> completedTicketsInPeriod = allTickets.stream()
                .filter(t -> t.getStatue() == Status.Termine)
                // MODIFICATION ICI: Utiliser getDateCloture()
                .filter(t -> t.getDateCloture() != null && t.getDateCloture().isAfter(startDate))
                .collect(Collectors.toList());
        Map<String, Long> groupedCounts = new HashMap<>();

        if ("employee".equalsIgnoreCase(groupBy)) {
            // Regrouper par l'employé affecté
            groupedCounts = completedTicketsInPeriod.stream()
                    .filter(t -> t.getIdUtilisateur() != null)
                    .collect(Collectors.groupingBy(
                            t -> t.getIdUtilisateur().getPrenom() + " " + t.getIdUtilisateur().getNom(),
                            Collectors.counting()));
            // Inclure les tickets terminés sans assigné (si pertinent)
            long unassignedCompleted = completedTicketsInPeriod.stream()
                    .filter(t -> t.getIdUtilisateur() == null)
                    .count();
            if (unassignedCompleted > 0) {
                groupedCounts.put("Non assigné", unassignedCompleted);
            }

        } else if ("team".equalsIgnoreCase(groupBy)) {
            // Regrouper par l'équipe (via le module du ticket)
            // C'est un peu plus complexe car le ticket est lié à un module, et le module à
            // une équipe.
            groupedCounts = completedTicketsInPeriod.stream()
                    .filter(t -> t.getModule() != null && t.getModule().getEquipe() != null)
                    .collect(Collectors.groupingBy(
                            t -> t.getModule().getEquipe().getDesignation(),
                            Collectors.counting()));
            // Inclure les tickets terminés sans module/équipe (si pertinent)
            long noTeamCompleted = completedTicketsInPeriod.stream()
                    .filter(t -> t.getModule() == null || t.getModule().getEquipe() == null)
                    .count();
            if (noTeamCompleted > 0) {
                groupedCounts.put("Sans équipe", noTeamCompleted);
            }
        } else {
            throw new IllegalArgumentException(
                    "Invalid groupBy parameter for performance stats. Must be 'employee' or 'team'.");
        }

        List<Map<String, Object>> result = groupedCounts.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("category", entry.getKey()); // Nom de l'employé ou de l'équipe
                    item.put("completedTickets", entry.getValue()); // Nombre de tickets terminés
                    return item;
                })
                .sorted(Comparator.comparing(item -> (Long) item.get("completedTickets"), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return result;
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getOverdueTickets() {
        log.debug("Request to get overdue tickets");
        LocalDateTime now = LocalDateTime.now();

        // Récupérer les tickets qui ne sont ni terminés ni refusés
        // ET dont la date d'échéance est passée (isBefore(now))
        List<Ticket> overdueTickets = ticketRepository.findAll().stream()
                .filter(t -> t.getStatue() != Status.Termine && t.getStatue() != Status.Refuse)
                .filter(t -> t.getDate_echeance() != null && t.getDate_echeance().isBefore(now))
                .collect(Collectors.toList());

        // Convertir en DTOs de réponse légers pour éviter les boucles infinies ou trop
        // de données
        return TicketFactory.toDTOsLight(overdueTickets);
    }
}