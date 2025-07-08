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

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {
  
    Collection<Client> findByActif(Boolean actif);
  
    @Query("SELECT c.countryCode as regionName, COUNT(c) as totalClients, SUM(CASE WHEN c.actif = true THEN 1 ELSE 0 END) as activeClients FROM Client c WHERE c.countryCode IS NOT NULL GROUP BY c.countryCode")
    List<Map<String, Object>> getStatsByCountry();

    @Query("SELECT c.regionName as regionName, COUNT(c) as totalClients, SUM(CASE WHEN c.actif = true THEN 1 ELSE 0 END) as activeClients FROM Client c WHERE c.countryCode = :countryCode AND c.regionName IS NOT NULL GROUP BY c.regionName")
    List<Map<String, Object>> getStatsByRegionNameForCountry(@Param("countryCode") String countryCode);

    /**
     * ✅ NOUVEAU : Recherche des clients par un terme dans le nom ou l'email.
     * @param term Le terme de recherche.
     * @return Une liste de clients correspondants.
     */
    @Query("SELECT c FROM Client c WHERE c.nomComplet LIKE %:term% OR c.email LIKE %:term%")
    List<Client> searchByTerm(@Param("term") String term);
}
