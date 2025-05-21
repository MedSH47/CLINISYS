package com.csys.template.repository;

import com.csys.template.domain.Fichiersjointes;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fichiersjointes entity.
 */
@Repository
public interface FichiersjointesRepository extends JpaRepository<Fichiersjointes, Integer> {
}

