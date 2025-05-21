package com.csys.template.repository;

import com.csys.template.domain.Commentaire;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Commentaire entity.
 */
@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
}

