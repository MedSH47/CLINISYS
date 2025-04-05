package com.csys.template.repository;

import com.csys.template.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
    Module findOneById(Integer id);
}
