package com.devsuperior.hrworker.repository;

import com.devsuperior.hrworker.model.MobileLogs;
import com.devsuperior.hrworker.model.MongoLogsRequestModel;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoLogsRepository extends MongoRepository <MobileLogs, String>{
    //Optional<MongoLogsRequestModel> findById(String idApp);
}
