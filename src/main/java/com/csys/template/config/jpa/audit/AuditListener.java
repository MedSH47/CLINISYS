package com.csys.template.config.jpa.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Revision revision = (Revision) revisionEntity;
        
        // Récupère l'utilisateur depuis le contexte de sécurité de Spring
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username;
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            username = "SYSTEM"; // ou "anonymous"
        } else {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }
        
        revision.setUserCreate(username);
    }
}