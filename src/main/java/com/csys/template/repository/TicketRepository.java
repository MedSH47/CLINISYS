package com.csys.template.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>, QuerydslPredicateExecutor<Ticket>,JpaSpecificationExecutor<Ticket>  {
        List<Ticket> findByDateCreationAfter(LocalDateTime date);

}