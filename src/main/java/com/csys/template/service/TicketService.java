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
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    private final JPAQueryFactory queryFactory;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final ModuleRepository moduleRepository;
    private final UtilisateurRepository utilisateurRepository;

    public TicketService(TicketRepository ticketRepository, JPAQueryFactory queryFactory,
            ModuleRepository moduleRepository, UtilisateurRepository utilisateurRepository,
            SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
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

        notifyTeamLeadOnModuleAssignment(ticket);

        sendTargetedNotification(ticket, "TICKET_CREATED", "Nouveau ticket créé : "+ticket.getTitre() );

        return TicketFactory.toResponseDTO(ticket);
    }

    public TicketResponseDTO update(Integer ticketId, TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to update Ticket: {}", ticketId);
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));

        Integer oldUserId = existingTicket.getIdUtilisateur() != null ? existingTicket.getIdUtilisateur().getId()
                : null;
        Integer oldModuleId = existingTicket.getModule() != null ? existingTicket.getModule().getId() : null;
        Status oldStatus = existingTicket.getStatue();

        TicketFactory.updateFromDTO(existingTicket, ticketRequestDTO);
        Ticket updatedTicket = ticketRepository.save(existingTicket);

        if (ticketRequestDTO.getIdUtilisateur() != null && !ticketRequestDTO.getIdUtilisateur().equals(oldUserId)) {
            Utilisateur assignedUser = utilisateurRepository.findById(ticketRequestDTO.getIdUtilisateur()).orElse(null);
            if (assignedUser != null) {
                notificationService.createAndSendNotification(assignedUser,
                        "Le ticket  " + updatedTicket.getTitre() + " vous a été assigné."+"\n verifier votre tickets", "/tickets/" + ticketId);
            }
        }

        Integer newModuleId = updatedTicket.getModule() != null ? updatedTicket.getModule().getId() : null;
        if (!Objects.equals(oldModuleId, newModuleId)) {
            notifyTeamLeadOnModuleAssignment(updatedTicket);
        }

        Status newStatus = updatedTicket.getStatue();
        if (newStatus != oldStatus) {
            if (newStatus == Status.Termine) {
                notificationService.createAndSendNotificationToUsers(
                        utilisateurRepository.findByRole(Role.A),
                        "Le ticket " + updatedTicket.getTitre() + " a été terminé.",
                        "/tickets/" + ticketId);
            } else if (newStatus == Status.Refuse) {
                notificationService.createAndSendNotificationToUsers(utilisateurRepository.findByRole(Role.A),
                        "Le ticket  " + updatedTicket.getTitre() + " a été refusé.", "/tickets/" + ticketId);
            }
        }

        sendTargetedNotification(
                updatedTicket,
                "TICKET_UPDATED",
                "Le ticket " + updatedTicket.getTitre() + " a été mis à jour");
        return TicketFactory.toResponseDTO(updatedTicket);
    }

    private void notifyTeamLeadOnModuleAssignment(Ticket ticket) {
        if (ticket.getModule() != null && ticket.getModule().getId() != null) {
            // ✅ CORRECTION APPLIQUÉE ICI
            com.csys.template.domain.Module module = moduleRepository.findById(ticket.getModule().getId()).orElse(null);

            if (module != null && module.getEquipe() != null && module.getEquipe().getChefEquipe() != null) {
                Utilisateur teamLead = module.getEquipe().getChefEquipe();
                String message = String.format("Nouveau ticket '%s' assigné au module '%s'. ", ticket.getTitre(),
                        module.getDesignation());
                String link = "/tickets/" + ticket.getId();
                notificationService.createAndSendNotification(teamLead, message, link);
                log.info("Notification envoyée au chef d'équipe {} pour l'assignation du ticket #{} au module {}",
                        teamLead.getLogin(), ticket.getId(), module.getDesignation());
            }
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ticket.NotFound"));
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
        Set<String> sentToUsers = new HashSet<>();
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

    @Transactional(readOnly = true)
    public TicketResponseDTO findOne(Integer id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return TicketFactory.toResponseDTO(ticket);
    }

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

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> findAllParents() {
        QTicket ticket = QTicket.ticket;
        List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll(ticket.parentTicket.isNull());
        return TicketFactory.toResponseDTOsParents(tickets);
    }

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
        List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll(ticket.idUtilisateur.id.eq(userId));
        return TicketFactory.toResponseDTOs(tickets);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> searchByTerm(String term) {
        if (term == null || term.isBlank() || term.length() < 2) {
            return Collections.emptyList();
        }
        List<Ticket> results = ticketRepository.searchByTerm(term);
        return TicketFactory.toResponseDTOs(results);
    }
}