package com.csys.template.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.Commentaire;

/**
 * Spring Data JPA repository for the Commentaire entity.
 */
@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
    List<Commentaire> findByDateCommentaireAfter(LocalDateTime date);

}

