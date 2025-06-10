package com.csys.template.log.repository;

import com.csys.template.log.demain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findTop10ByOrderByTimestampDesc();

    @Query("SELECT l FROM Log l WHERE l.entityName = :entityName AND l.entityId = :entityId")
    List<Log> findByEntity(@Param("entityName") String entityName,
                          @Param("entityId") String entityId);

    List<Log> findByLogType(Log.LogType logType);
}