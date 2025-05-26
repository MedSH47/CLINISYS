package com.csys.template.repository;

import com.csys.template.domain.DocumentJointes;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentJointes entity.
 */
@Repository
public interface DocumentJointesRepository extends JpaRepository<DocumentJointes, Integer> {
}

