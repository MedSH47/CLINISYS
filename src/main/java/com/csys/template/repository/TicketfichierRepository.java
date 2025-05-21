package com.csys.template.repository;

import com.csys.template.domain.Ticketfichier;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ticketfichier entity.
 */
@Repository
public interface TicketfichierRepository extends JpaRepository<Ticketfichier, Integer> {
}

