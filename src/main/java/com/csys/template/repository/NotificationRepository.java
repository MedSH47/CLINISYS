package com.csys.template.repository;

import com.csys.template.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    
    // Trouve toutes les notifications pour un utilisateur donné, triées par date de création (les plus récentes en premier)
    List<Notification> findByUtilisateurIdOrderByCreatedAtDesc(Integer utilisateurId);
}