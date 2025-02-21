package com.csys.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket , Integer> {

}
