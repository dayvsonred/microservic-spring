package com.devsuperior.hrprocess.repository;

import com.devsuperior.hrprocess.entities.LogsForDayTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LogsForDayTimeRepository extends CrudRepository<LogsForDayTime, Long> {

    //@Query("SELECT id, created_date, data_end, num_requests, orden, period_end, period_start, start, status, logs_for_day_id
    // FROM public.logs_for_day_time where logs_for_day_id = ?1
    // and data_end is null
    // and start = false");
//    @Query("SELECT u.logs_for_day_id FROM logs_for_day_time u WHERE u.logs_for_day_id = ?1")
//    LogsForDayTime getLogsTimeNextProcess(String id );

}
