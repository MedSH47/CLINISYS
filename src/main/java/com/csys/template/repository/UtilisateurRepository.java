package com.csys.template.repository;

import com.csys.template.domain.Utilisateur;


import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Utilisateur entity.
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>,QuerydslPredicateExecutor<Utilisateur> ,JpaSpecificationExecutor<Utilisateur>{
    Utilisateur findByemail(String email);
    Utilisateur findByLogin(String login);
}

