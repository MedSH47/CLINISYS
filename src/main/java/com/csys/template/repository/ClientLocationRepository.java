package com.csys.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.ClientLocation;

@Repository
public interface ClientLocationRepository extends JpaRepository<ClientLocation, Integer> {
    // Vous pouvez ajouter des méthodes personnalisées si besoin, par exemple findByClientId
}
