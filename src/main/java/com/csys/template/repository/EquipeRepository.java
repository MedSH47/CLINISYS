package com.csys.template.repository;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.Utilisateur;

import java.lang.Integer;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Equipe entity.
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer>,QuerydslPredicateExecutor<Equipe>,JpaSpecificationExecutor<Equipe> {
    boolean existsBydesignation(String designation);

    List<Equipe> findByChefEquipe(Utilisateur chef_equipe);
}

