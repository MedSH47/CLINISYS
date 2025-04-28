package com.csys.template.repository;

import com.csys.template.domain.Utilisateur;
import java.lang.Boolean;
import java.lang.Integer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
  List<Utilisateur> findByActif(Boolean actif);
  Utilisateur findBylogin(String login);
  boolean existsBylogin(String login);
}

