// src/main/java/com/csys/template/web/rest/ressource/AuditResource.java
package com.csys.template.web.rest.ressource;

import com.csys.template.dtoResponse.AuditLogDTO;
import com.csys.template.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditResource {

    private final AuditService auditService;

    public AuditResource(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * Récupère l'historique d'un ticket spécifique.
     * @param id L'ID du ticket.
     * @return Une liste d'événements d'audit.
     */
    @GetMapping("/history/ticket/{id}")
    public ResponseEntity<List<AuditLogDTO>> getTicketHistory(@PathVariable Integer id) {
        List<AuditLogDTO> history = auditService.getTicketHistory(id);
        return ResponseEntity.ok(history);
    }

    /**
     * ✅ NOUVEAU : Récupère l'historique d'un client spécifique.
     * @param id L'ID du client.
     * @return Une liste d'événements d'audit.
     */
    @GetMapping("/history/client/{id}")
    public ResponseEntity<List<AuditLogDTO>> getClientHistory(@PathVariable Integer id) {
        List<AuditLogDTO> history = auditService.getClientHistory(id);
        return ResponseEntity.ok(history);
    }

    /**
     * ✅ NOUVEAU : Récupère l'historique d'un utilisateur spécifique.
     * @param id L'ID de l'utilisateur.
     * @return Une liste d'événements d'audit.
     */
    @GetMapping("/history/utilisateur/{id}")
    public ResponseEntity<List<AuditLogDTO>> getUtilisateurHistory(@PathVariable Integer id) {
        List<AuditLogDTO> history = auditService.getUtilisateurHistory(id);
        return ResponseEntity.ok(history);
    }
}
