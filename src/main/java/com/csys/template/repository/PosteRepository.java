package com.csys.template.repository;

import com.csys.template.domain.Poste;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Poste entity.
 */
@Repository
public interface PosteRepository extends JpaRepository<Poste, Integer> {
}

