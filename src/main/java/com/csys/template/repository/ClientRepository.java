package com.csys.template.repository;

import com.csys.template.domain.Client;
import java.lang.Boolean;
import java.lang.Integer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>,JpaSpecificationExecutor<Client> {
  Collection<Client> findByActif(Boolean actif);
  @Query("SELECT c.countryCode as regionName, COUNT(c) as totalClients, SUM(CASE WHEN c.actif = true THEN 1 ELSE 0 END) as activeClients " +
           "FROM Client c WHERE c.countryCode IS NOT NULL GROUP BY c.countryCode")
    List<Map<String, Object>> getStatsByCountry();

    // Nouvelle requête pour les statistiques par gouvernorat tunisien
    @Query("SELECT c.regionName as regionName, COUNT(c) as totalClients, SUM(CASE WHEN c.actif = true THEN 1 ELSE 0 END) as activeClients " +
           "FROM Client c WHERE c.countryCode = :countryCode AND c.regionName IS NOT NULL GROUP BY c.regionName")
    List<Map<String, Object>> getStatsByRegionNameForCountry(@Param("countryCode") String countryCode);
}

