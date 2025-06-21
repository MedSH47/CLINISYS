package com.csys.template.repository;

import com.csys.template.domain.Client;
import java.lang.Boolean;
import java.lang.Integer;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>,JpaSpecificationExecutor<Client> {
  Collection<Client> findByActif(Boolean actif);
}

