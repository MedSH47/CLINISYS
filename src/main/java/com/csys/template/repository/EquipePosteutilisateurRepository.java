package com.csys.template.repository;

import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.EquipePosteutilisateurPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipePosteutilisateur entity.
 */
@Repository
public interface EquipePosteutilisateurRepository extends JpaRepository<EquipePosteutilisateur, EquipePosteutilisateurPK> {
}

