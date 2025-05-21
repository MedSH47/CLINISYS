package com.csys.template.repository;

import com.csys.template.domain.Utilisateur;
import java.lang.Integer;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
Optional< Utilisateur> findByemail(String email);

Boolean existsByemail(String email);
}

