package com.csys.template.repository;

import com.csys.template.domain.Utilisateur;
import java.lang.Boolean;
import java.lang.Integer;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Utilisateur entity.
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
  List<Utilisateur> findByActif(Boolean actif);
  Optional<Utilisateur> findBylogin(String login);

}

