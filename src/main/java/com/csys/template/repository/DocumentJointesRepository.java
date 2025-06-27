package com.csys.template.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.DocumentJointes;

/**
 * Spring Data JPA repository for the DocumentJointes entity.
 */
@Repository
public interface DocumentJointesRepository extends JpaRepository<DocumentJointes, Integer> {
    List<DocumentJointes> findByDateDocumentAfter(LocalDateTime date);

}

