package com.devsuperior.hrprocess.repository;

import com.devsuperior.hrprocess.entities.LogsForDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface LogsForDayRepository extends CrudRepository<LogsForDay, Long> {
    LogsForDay findByLogProcessedData(LocalDate buscar);

//    @Query("SELECT u FROM User u WHERE u.status = 1")
//    LogsForDay findMaxlogForAnDay();

}
