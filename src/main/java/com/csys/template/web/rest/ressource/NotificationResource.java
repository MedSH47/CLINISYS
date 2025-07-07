// Fichier à modifier : src/main/java/com/csys/template/web/rest/ressource/NotificationResource.java

package com.csys.template.web.rest.ressource;

import com.csys.template.domain.Notification;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoResponse.NotificationResponseDTO; // ✅ IMPORT DTO
import com.csys.template.factory.NotificationFactory;       // ✅ IMPORT FACTORY
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final NotificationService notificationService;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public NotificationResource(NotificationService notificationService, UtilisateurRepository utilisateurRepository) {
        this.notificationService = notificationService;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * GET /notifications : Récupère toutes les notifications pour l'utilisateur authentifié.
     * ✅ CORRECTION : Renvoie maintenant une liste de NotificationResponseDTO.
     */
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getUserNotifications(Principal principal) {
        // La logique pour trouver l'utilisateur ne change pas.
        Utilisateur currentUser = utilisateurRepository.findByLogin(principal.getName());
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }
        
        // Le service renvoie toujours une liste d'entités.
        List<Notification> notifications = notificationService.findByUser(currentUser.getId());
        
        // ✅ C'est ici que la magie opère : nous convertissons la liste d'entités en liste de DTOs.
        List<NotificationResponseDTO> dtos = NotificationFactory.toResponseDTOs(notifications);
        
        return ResponseEntity.ok(dtos);
    }

    /**
     * POST /notifications/{id}/read : Marque une notification spécifique comme lue.
     * (Cet endpoint ne renvoyant rien, il n'a pas besoin de modification).
     */
    @PostMapping("/notifications/{id}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Integer id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}