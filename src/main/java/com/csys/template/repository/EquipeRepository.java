package com.csys.template.repository;

import com.csys.template.domain.Equipe;
import com.querydsl.core.Query;

import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Equipe entity.
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer>,QuerydslPredicateExecutor<Equipe> {
    boolean existsBydesignation(String designation);
}

