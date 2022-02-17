package com.devsuperior.hrprocess.repository.Mongo;

import com.devsuperior.hrprocess.model.document.MobileLogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface MongoLogsRepository extends MongoRepository <MobileLogs, String>{

    @Query("{'requestDate': {$gte: ?0, $lte:?1 }}")
    List<MobileLogs> findMobileLogsRequestDateBetween(ZonedDateTime startDate, ZonedDateTime endDate);

    @Query("{'requestDate': {$gte: ?0, $lte:?1 }}")
    List<MobileLogs> findMobileLogsByDateBetween(Date startDate, Date endDate);

}
