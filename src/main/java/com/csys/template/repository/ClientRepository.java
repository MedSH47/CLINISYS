package com.csys.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csys.template.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findOneById(Integer id);
}
