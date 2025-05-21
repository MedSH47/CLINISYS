package com.csys.template.repository;

import com.csys.template.domain.EquipePoste;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipePoste entity.
 */
@Repository
public interface EquipePosteRepository extends JpaRepository<EquipePoste, Integer> {
}

