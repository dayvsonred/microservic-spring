package com.devsuperior.hrworker.repository;

import com.devsuperior.hrworker.model.MobileLogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MongoLogsRepository extends MongoRepository <MobileLogs, String>{
}
