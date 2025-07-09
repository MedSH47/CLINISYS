package com.csys.template.web.rest.ressource;

import com.csys.template.config.MailSender;
import com.csys.template.dtoResponse.ClientResponseDTO;
import com.csys.template.dtoResponse.ModuleResponseDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.service.TicketService;
import com.csys.template.service.UtilisateurService;
import com.csys.template.service.ClientService; // Import du service Client
import com.csys.template.service.ModuleService; // Import du service Module
import com.csys.template.util.Helper;
import com.csys.template.util.FormulairesHtml;
import com.csys.template.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MailingServiceRessource {

    private static final Logger log = LoggerFactory.getLogger(MailingServiceRessource.class);

    private final MailSender mailSender;
    private final UtilisateurService utilisateurService;
    private final TicketService ticketService;
    private final ClientService clientService; // Service Client injecté
    private final ModuleService moduleService; // Service Module injecté
    private final JwtUtil jwtUtil;

    private static final long CODE_EXPIRE_AFTER_MINUTES = 5;
    private final Map<String, ResetCode> resetCodes = new ConcurrentHashMap<>();
    
    public MailingServiceRessource(MailSender mailSender, UtilisateurService utilisateurService, TicketService ticketService, ClientService clientService, ModuleService moduleService, JwtUtil jwtUtil) {
        this.mailSender = mailSender;
        this.utilisateurService = utilisateurService;
        this.ticketService = ticketService;
        this.clientService = clientService;
        this.moduleService = moduleService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendForgotPasswordEmail(@RequestParam String email) {
        final String trimmedEmail = email.trim();
        UtilisateurResponseDTO utilisateur = utilisateurService.findByEmail(trimmedEmail);

        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }

        String code = Helper.generateCode();
        storeCode(trimmedEmail, code);

        try {
            mailSender.sendHtmlMail(utilisateur.getEmail(), "Demande de réinitialisation de votre mot de passe", FormulairesHtml.genererHtmlReinitialisationMotDePasse(utilisateur.getNom(), code));
            return ResponseEntity.ok("E-mail de réinitialisation envoyé.");
        } catch (MessagingException e) {
            log.error("Échec de l'envoi de l'e-mail pour '{}'.", trimmedEmail, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'envoi de l'e-mail.");
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyResetCode(@RequestParam String email, @RequestParam String code) {
        final String trimmedEmail = email.trim();
        
        if (verifyCode(trimmedEmail, code.trim())) {
            String resetToken = jwtUtil.generatePasswordResetToken(trimmedEmail);
            return ResponseEntity.ok(Collections.singletonMap("resetToken", resetToken));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code invalide ou expiré.");
        }
    }

    @PostMapping("/reset-password-jwt")
    public ResponseEntity<?> resetPasswordWithJwt(@Valid @RequestBody PasswordResetRequest request) {
        try {
            if (!jwtUtil.validateToken(request.getResetToken())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Jeton de réinitialisation invalide ou expiré.");
            }
            String emailFromToken = jwtUtil.extractUsername(request.getResetToken());
            if (!emailFromToken.equalsIgnoreCase(request.getEmail().trim())) {
                log.warn("Tentative de réinitialisation de mot de passe frauduleuse ! L'e-mail du jeton ({}) ne correspond pas à l'e-mail de la requête ({}).", emailFromToken, request.getEmail());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Le jeton ne correspond pas à l'utilisateur spécifié.");
            }
            utilisateurService.updatePassword(emailFromToken, request.getNewPassword());
            return ResponseEntity.ok("Votre mot de passe a été mis à jour avec succès.");
        } catch (Exception e) {
            log.error("Erreur lors de la réinitialisation du mot de passe avec JWT : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Jeton invalide ou une erreur interne est survenue.");
        }
    }

    @PostMapping("/notify-late-ticket/{ticketId}")
    public ResponseEntity<?> sendLateTicketNotification(@PathVariable @NotNull Integer ticketId) {
        TicketResponseDTO ticket = ticketService.findOne(ticketId);
        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket non trouvé pour l'ID : " + ticketId);
        }

        if (ticket.getIdUtilisateur() == null) {
            log.warn("Tentative de notification pour le ticket en retard #{} sans utilisateur assigné.", ticketId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le ticket n'a pas d'utilisateur assigné.");
        }
        
        UtilisateurResponseDTO utilisateur = utilisateurService.findOne(ticket.getIdUtilisateur().getId());
        if (utilisateur == null || utilisateur.getEmail() == null || utilisateur.getEmail().isEmpty()) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur assigné non trouvé ou sans e-mail.");
        }

        // CORRECTION : Récupérer les noms du client et du module via leurs services
        String nomClient = "N/A";
        if (ticket.getIdClient() != null) {
            ClientResponseDTO client = clientService.findOne(ticket.getIdClient().getId());
            if (client != null) {
                nomClient = client.getNomComplet();
            }
        }

        String nomModule = "N/A";
        if (ticket.getIdModule() != null) {
            ModuleResponseDTO module = moduleService.findOne(ticket.getIdModule().getId());
            if (module != null) {
                nomModule = module.getDesignation();
            }
        }

        FormulairesHtml.TicketInfo ticketInfo = new FormulairesHtml.TicketInfo();
        ticketInfo.setTitre(ticket.getTitre());
        ticketInfo.setDescription(ticket.getDescription());
        ticketInfo.setPriorite(ticket.getPriorite() != null ? ticket.getPriorite().name() : "N/A");
        ticketInfo.setStatut(ticket.getStatue() != null ? ticket.getStatue().name() : "N/A");
        ticketInfo.setDateEcheance(ticket.getDate_echeance());
        ticketInfo.setNomClient(nomClient);
        ticketInfo.setNomModule(nomModule);
        ticketInfo.setNomUtilisateur(utilisateur.getNom());

        try {
            String emailBody = FormulairesHtml.genererHtmlTicketEnRetard(utilisateur.getNom(), ticketInfo);
            String subject = "Alerte : Ticket en Retard - " + ticket.getTitre();
            mailSender.sendHtmlMail(utilisateur.getEmail(), subject, emailBody);
            return ResponseEntity.ok("Notification de ticket en retard envoyée avec succès à " + utilisateur.getEmail());
        } catch (MessagingException e) {
            log.error("Échec de l'envoi de l'e-mail de notification de retard pour '{}'.", utilisateur.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'envoi de l'e-mail.");
        }
    }
    
    // --- Méthodes utilitaires et classes internes ---

    private void storeCode(String email, String code) {
        resetCodes.put(email, new ResetCode(code, LocalDateTime.now()));
    }

    private boolean verifyCode(String email, String code) {
        ResetCode storedCode = resetCodes.get(email);
        if (storedCode != null && storedCode.getCode().equals(code)) {
            if (storedCode.getTimestamp().plusMinutes(CODE_EXPIRE_AFTER_MINUTES).isAfter(LocalDateTime.now())) {
                resetCodes.remove(email);
                return true;
            }
        }
        return false;
    }

    private static class ResetCode {
        private final String code;
        private final LocalDateTime timestamp;
        public ResetCode(String code, LocalDateTime timestamp) { this.code = code; this.timestamp = timestamp; }
        public String getCode() { return code; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    public static class PasswordResetRequest {
        @NotEmpty(message = "L'e-mail ne peut pas être vide.")
        @Email(message = "Le format de l'e-mail est invalide.")
        private String email;

        @NotEmpty(message = "Le jeton ne peut pas être vide.")
        private String resetToken;

        @NotEmpty(message = "Le nouveau mot de passe ne peut pas être vide.")
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
        private String newPassword;

        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getResetToken() { return resetToken; }
        public void setResetToken(String resetToken) { this.resetToken = resetToken; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}
