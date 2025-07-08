package com.csys.template.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>, QuerydslPredicateExecutor<Ticket>,JpaSpecificationExecutor<Ticket>  {
        List<Ticket> findByDateCreationAfter(LocalDateTime date);
        /**
     * ✅ NOUVEAU : Recherche des tickets par un terme dans le titre ou la description.
     * Limite les résultats pour de meilleures performances de l'interface.
     * @param term Le terme de recherche.
     * @return Une liste de tickets correspondants.
     */
    @Query("SELECT t FROM Ticket t WHERE t.titre LIKE %:term% OR t.description LIKE %:term%")
    List<Ticket> searchByTerm(@Param("term") String term);
}