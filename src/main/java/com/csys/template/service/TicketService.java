package com.csys.template.service;

import com.csys.template.domain.QTicket;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Role;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoProjection.*;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.factory.TicketFactory;
import com.csys.template.repository.ModuleRepository;
import com.csys.template.repository.TicketRepository;
import com.csys.template.repository.UtilisateurRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {

    private final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final JPAQueryFactory queryFactory; // Injection de JPAQueryFactory
    private final SimpMessagingTemplate messagingTemplate; // <-- **2. DÉCLARATION DU CHAMP**
    private final NotificationService notificationService;
    

    // Le reste des dépendances pour les opérations d'écriture
    private final ModuleRepository moduleRepository;
    private final UtilisateurRepository utilisateurRepository;

    public TicketService(TicketRepository ticketRepository, JPAQueryFactory queryFactory,
            ModuleRepository moduleRepository, UtilisateurRepository utilisateurRepository,
             SimpMessagingTemplate messagingTemplate,NotificationService notificationService) {
        this.ticketRepository = ticketRepository;
        this.queryFactory = queryFactory;
        this.moduleRepository = moduleRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public TicketResponseDTO save(TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to save Ticket: {}", ticketRequestDTO);
        Ticket ticket = TicketFactory.toEntity(ticketRequestDTO);
        ticket = ticketRepository.save(ticket);
        sendTargetedNotification(ticket, "TICKET_CREATED", "Nouveau ticket créé : #" + ticket.getId());

        return TicketFactory.toResponseDTO(ticket);
    }

    public TicketResponseDTO update(Integer ticketId, TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to update Ticket: {}", ticketId);
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
        // La logique métier de notification, etc. est préservée
        if (ticketRequestDTO.getIdUtilisateur()!=null && ticketRequestDTO.getIdUtilisateur() != 0) {
            String message = "Le ticket #" + ticketId + " a été assigné à " + ticketRequestDTO.getIdUtilisateur();
            String link = "/tickets/" + ticketId; // Un lien direct vers le ticket
            Utilisateur user = utilisateurRepository.findById(ticketRequestDTO.getIdUtilisateur()).orElse(null);
            notificationService.createAndSendNotification(user, message, link);
        }
        TicketFactory.updateFromDTO(existingTicket, ticketRequestDTO);
        ticketRepository.save(existingTicket);
        sendTargetedNotification(existingTicket, "TICKET_UPDATED", "Le ticket #" + ticketId + " a été mis à jour.");

        return TicketFactory.toResponseDTO(existingTicket);
    }

    public ResponseEntity<?> delete(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
        // La logique métier de vérification est préservée
        if (ticket.getModule() != null || ticket.getIdUtilisateur() != null || !ticket.getChildTickets().isEmpty()) {
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body("Le ticket ne peut être supprimé car il a des dépendances.");
        }
        ticketRepository.deleteById(id);
        sendTargetedNotification(ticket, "TICKET_DELETED", "Le ticket #" + id + " a été supprimé.");

        return ResponseEntity.ok().build();
    }

   private void sendTargetedNotification(Ticket ticket, String type, String message) {
    if (ticket == null) {
        log.warn("Ticket null reçu pour notification ciblée.");
        return;
    }

    NotificationDTO notification = new NotificationDTO(ticket, message, type);
    Set<String> sentToUsers = new HashSet<>(); // pour éviter les doublons de login
    Set<Utilisateur> recipients = new HashSet<>();

    if (ticket.getIdUtilisateur() != null) {
        recipients.add(ticket.getIdUtilisateur());
    }

    if (ticket.getModule() != null && ticket.getModule().getEquipe() != null
            && ticket.getModule().getEquipe().getChefEquipe() != null) {
        recipients.add(ticket.getModule().getEquipe().getChefEquipe());
    }

    List<Utilisateur> admins = utilisateurRepository.findByRole(Role.A);
    recipients.addAll(admins);

    log.info("Sending targeted notifications to {} recipients for ticket #{}", recipients.size(), ticket.getId());

    for (Utilisateur user : recipients) {
        if (user != null && user.getLogin() != null && !user.getLogin().isBlank()) {
            if (sentToUsers.add(user.getLogin())) {
                try {
                    messagingTemplate.convertAndSendToUser(user.getLogin(), "/queue/notifications", notification);
                    log.debug("Notification envoyée à {}", user.getLogin());
                } catch (Exception e) {
                    log.error("Erreur d'envoi de notification à {} : {}", user.getLogin(), e.getMessage(), e);
                }
            }
        } else {
            log.warn("Utilisateur null ou login absent : notification ignorée.");
        }
    }
}


    // --- Méthodes de lecture refactorisées ---
    @Transactional(readOnly = true)
    public TicketResponseDTO findOne(Integer id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return TicketFactory.toResponseDTO(ticket);
    }

    /**
     * REFACTORISÉ : Utilise QueryDSL (BooleanBuilder) pour un filtrage
     * dynamique et propre. AVANT : Utilisait un WhereClauseBuilder
     * personnalisé.
     */
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> findAll(Status statue, Integer idModule, Priorite priorite, Boolean[] actifs) {
        log.debug("Request to get All Tickets with filters");
        QTicket ticket = QTicket.ticket;
        BooleanBuilder predicate = new BooleanBuilder();

        if (statue != null) {
            predicate.and(ticket.statue.eq(statue));
        }
        if (idModule != null) {
            predicate.and(ticket.module.id.eq(idModule));
        }
        if (priorite != null) {
            predicate.and(ticket.priorite.eq(priorite));
        }
        if (actifs != null && actifs.length > 0) {
            predicate.and(ticket.actif.in(actifs));
        }

        List<Ticket> result = (List<Ticket>) ticketRepository.findAll(predicate);
        return TicketFactory.toResponseDTOs(result);
    }

    /**
     * REFACTORISÉ : Le filtre se fait maintenant dans la base de données. AVANT
     * : Récupérait TOUS les tickets puis filtrait en mémoire. Très inefficace.
     */
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> findAllParents() {
        QTicket ticket = QTicket.ticket;
        List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll(ticket.parentTicket.isNull());
        return TicketFactory.toResponseDTOsParents(tickets);
    }

    /**
     * REFACTORISÉ : Utilise une requête d'agrégation SQL (via QueryDSL) et une
     * projection DTO. AVANT : Récupérait TOUS les tickets, puis utilisait un
     * stream pour grouper en mémoire.
     */
    @Transactional(readOnly = true)
    public List<StatusCountDTO> getCountsByStatus() {
        log.debug("Request to get ticket counts by status");
        QTicket ticket = QTicket.ticket;
        return queryFactory
                .select(Projections.constructor(StatusCountDTO.class,
                        ticket.statue,
                        ticket.id.count()))
                .from(ticket)
                .groupBy(ticket.statue)
                .fetch();
    }

    /**
     * REFACTORISÉ : Utilise une projection DTO pour ne sélectionner que les
     * champs nécessaires. AVANT : Récupérait les entités Ticket complètes, puis
     * mappait manuellement vers une Map.
     */
    @Transactional(readOnly = true)
    public List<TicketCalendarEventDTO> getCalendarEvents() {
        log.debug("Request to get calendar events from tickets");
        QTicket ticket = QTicket.ticket;
        return queryFactory
                .select(Projections.constructor(TicketCalendarEventDTO.class,
                        ticket.id,
                        ticket.titre,
                        ticket.date_echeance,
                        ticket.priorite,
                        ticket.statue,
                        ticket.idUtilisateur.nom))
                .from(ticket)
                .where(ticket.date_echeance.isNotNull())
                .fetch();
    }

    /**
     * REFACTORISÉ : Utilise des requêtes de comptage ciblées, beaucoup plus
     * rapides. AVANT : Récupérait TOUS les tickets puis les parcourait
     * plusieurs fois en mémoire.
     */
    @Transactional(readOnly = true)
    public GlobalTicketCountDTO getGlobalTicketCounts() {
        log.debug("Request to get global ticket counts");
        QTicket ticket = QTicket.ticket;

        long totalTickets = ticketRepository.count();
        long enAttente = ticketRepository.count(ticket.statue.eq(Status.En_attente));
        long enCours = ticketRepository.count(ticket.statue.eq(Status.En_cours));
        long accepte = ticketRepository.count(ticket.statue.eq(Status.Accepte));
        long refuse = ticketRepository.count(ticket.statue.eq(Status.Refuse));

        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
                .atStartOfDay();

        long terminesToday = ticketRepository
                .count(ticket.statue.eq(Status.Termine).and(ticket.dateCloture.goe(startOfToday)));
        long terminesThisWeek = ticketRepository
                .count(ticket.statue.eq(Status.Termine).and(ticket.dateCloture.goe(startOfWeek)));

        return new GlobalTicketCountDTO(totalTickets, enAttente, enCours, accepte, refuse, terminesToday,
                terminesThisWeek);
    }

    /**
     * REFACTORISÉ : Le groupement est fait par la base de données. AVANT :
     * Récupérait tous les tickets actifs puis groupait en mémoire.
     */
    @Transactional(readOnly = true)
    public List<ActiveTicketCountDTO> getActiveTicketsByAssigneeOrModule(String groupBy) {
        log.debug("Request to get active tickets grouped by: {}", groupBy);
        QTicket ticket = QTicket.ticket;

        BooleanBuilder predicate = new BooleanBuilder(ticket.statue.notIn(Status.Termine, Status.Refuse));

        if ("employee".equalsIgnoreCase(groupBy)) {
            return queryFactory
                    .select(Projections.constructor(ActiveTicketCountDTO.class,
                            ticket.idUtilisateur.nom.concat(" ").concat(ticket.idUtilisateur.prenom),
                            ticket.id.count()))
                    .from(ticket)
                    .where(predicate.and(ticket.idUtilisateur.isNotNull()))
                    .groupBy(ticket.idUtilisateur.nom, ticket.idUtilisateur.prenom)
                    .orderBy(ticket.id.count().desc())
                    .fetch();
        } else if ("module".equalsIgnoreCase(groupBy)) {
            return queryFactory
                    .select(Projections.constructor(ActiveTicketCountDTO.class,
                            ticket.module.designation,
                            ticket.id.count()))
                    .from(ticket)
                    .where(predicate.and(ticket.module.isNotNull()))
                    .groupBy(ticket.module.designation)
                    .orderBy(ticket.id.count().desc())
                    .fetch();
        } else {
            throw new IllegalArgumentException("Invalid groupBy parameter. Must be 'employee' or 'module'.");
        }
    }

    /**
     * REFACTORISÉ : Filtrage par date et groupement faits par la BDD. AVANT :
     * Filtrait et groupait tout en mémoire.
     */
    // @Transactional(readOnly = true)
    // public List<PerformanceStatDTO> getPerformanceStats(String groupBy, String period) {
    //     log.debug("Request to get performance stats by {} for period: {}", groupBy, period);
    //     QTicket ticket = QTicket.ticket;
    //     LocalDateTime startDate = "last_7_days".equalsIgnoreCase(period)
    //             ? LocalDateTime.now().minusDays(7).with(LocalTime.MIN)
    //             : LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    //     BooleanBuilder predicate = new BooleanBuilder(ticket.statue.eq(Status.Termine)
    //             .and(ticket.dateCloture.isNotNull())
    //             .and(ticket.dateCloture.goe(startDate)));
    //     if ("employee".equalsIgnoreCase(groupBy)) {
    //         return queryFactory
    //                 .select(Projections.constructor(PerformanceStatDTO.class,
    //                         ticket.idUtilisateur.nom.concat(" ").concat(ticket.idUtilisateur.prenom),
    //                         ticket.id.count()))
    //                 .from(ticket)
    //                 .where(predicate.and(ticket.idUtilisateur.isNotNull()))
    //                 .groupBy(ticket.idUtilisateur.nom, ticket.idUtilisateur.prenom)
    //                 .orderBy(ticket.id.count().desc())
    //                 .fetch();
    //     } else if ("team".equalsIgnoreCase(groupBy)) {
    //         return queryFactory
    //                 .select(Projections.constructor(PerformanceStatDTO.class,
    //                         ticket.module.equipe.designation,
    //                         ticket.id.count()))
    //                 .from(ticket)
    //                 .where(predicate.and(ticket.module.isNotNull()).and(ticket.module.equipe.isNotNull()))
    //                 .groupBy(ticket.module.equipe.designation)
    //                 .orderBy(ticket.id.count().desc())
    //                 .fetch();
    //     } else {
    //         throw new IllegalArgumentException("Invalid groupBy parameter. Must be 'employee' or 'team'.");
    //     }
    // }
    /**
     * REFACTORISÉ : Filtrage en BDD. AVANT : Filtrait en mémoire.
     */
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getOverdueTickets() {
        log.debug("Request to get overdue tickets");
        QTicket ticket = QTicket.ticket;

        BooleanBuilder predicate = new BooleanBuilder(ticket.statue.notIn(Status.Termine, Status.Refuse))
                .and(ticket.date_echeance.isNotNull())
                .and(ticket.date_echeance.before(LocalDateTime.now()));

        List<Ticket> overdueTickets = (List<Ticket>) ticketRepository.findAll(predicate);
        return TicketFactory.toDTOsLight(overdueTickets);
    }

    public List<TicketResponseDTO> findByUtilisateurId(Integer userId) {
        log.debug("Request to get all Tickets for user ID: {}", userId);
        QTicket ticket = QTicket.ticket;
        // Utilise QueryDSL pour trouver tous les tickets où l'ID de l'utilisateur
        // correspond.
        List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll(ticket.idUtilisateur.id.eq(userId));

        // Utilise la factory "complète" car le point d'entrée est le ticket, pas
        // l'utilisateur.
        // Il n'y a donc aucun risque de boucle de récursion.
        return TicketFactory.toResponseDTOs(tickets);
    }
}
