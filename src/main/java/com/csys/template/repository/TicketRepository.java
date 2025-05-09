package com.csys.template.repository;

import com.csys.template.domain.Ticket;
import com.csys.template.domain.Utilisateur;

import java.lang.Integer;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ticket entity.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
     Optional<Ticket> findByCollaborateurAndIdNot(Utilisateur collaborateur, Integer id);
}

