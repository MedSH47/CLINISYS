package com.csys.template.repository;

import com.csys.template.domain.Client;
import java.lang.Integer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    public boolean existsBynumClient(Integer id);
    @Query("SELECT c FROM Client c WHERE c.numClient = :id")
    public Client findBynumClient(@Param("id") Integer id);
    
}

