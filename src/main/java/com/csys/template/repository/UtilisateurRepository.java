package com.csys.template.repository;

import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Role;
import java.lang.Integer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>, QuerydslPredicateExecutor<Utilisateur>, JpaSpecificationExecutor<Utilisateur>{
    
    Utilisateur findByemail(String email);
    Utilisateur findByLogin(String login);
    List<Utilisateur> findByRole(Role role); 

    /**
     * ✅ NOUVEAU : Recherche des utilisateurs par un terme dans le nom, prénom, login ou email.
     * @param term Le terme de recherche.
     * @return Une liste d'utilisateurs correspondants.
     */
    @Query("SELECT u FROM Utilisateur u WHERE u.nom LIKE %:term% OR u.prenom LIKE %:term% OR u.login LIKE %:term% OR u.email LIKE %:term%")
    List<Utilisateur> searchByTerm(@Param("term") String term);
}
