package com.csys.template.repository;

import com.csys.template.domain.Avancement;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Avancement entity.
 */
@Repository
public interface AvancementRepository extends JpaRepository<Avancement, Integer> {
}

