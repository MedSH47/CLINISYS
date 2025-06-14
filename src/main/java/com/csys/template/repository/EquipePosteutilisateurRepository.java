package com.csys.template.repository;

import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.EquipePosteutilisateurPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipePosteutilisateur entity.
 */
@Repository
public interface EquipePosteutilisateurRepository extends JpaRepository<EquipePosteutilisateur, EquipePosteutilisateurPK> {
     // --- NEW REPOSITORY METHOD TO ADD ---
    // Option 1: Using a derived query method (if your PK has an 'idEquipe' property directly)
    // List<EquipePosteutilisateur> findById_IdEquipe(Integer idEquipe);
   List<EquipePosteutilisateur> findByEquipeId(Integer equipeId);
}

