package com.csys.template.config.jpa.audit.log.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.csys.template.config.jpa.audit.log.demain.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findTop10ByOrderByTimestampDesc();
    
     @Query("SELECT l FROM Log l WHERE l.entityName = :entityName AND l.entityId = :entityId")
    List<Log> findByEntity(@Param("entityName") String entityName, 
                          @Param("entityId") String entityId);
}
