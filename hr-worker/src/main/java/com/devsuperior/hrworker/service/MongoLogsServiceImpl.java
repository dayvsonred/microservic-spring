package com.devsuperior.hrworker.service;

import com.devsuperior.hrworker.model.MobileLogs;
import com.devsuperior.hrworker.repository.MongoLogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MongoLogsServiceImpl implements MongoLogsService {

    private static Logger logger = LoggerFactory.getLogger(MongoLogsServiceImpl.class);

    @Autowired
    private MongoLogsRepository mongoLogsRepository;

    @Override
    public MobileLogs create(MobileLogs object) {
        return mongoLogsRepository.insert(object);
    }

    @Override
    public List<MobileLogs> read() {
        return mongoLogsRepository.findAll();
    }

    @Override
    public MobileLogs update(MobileLogs object) {

        return mongoLogsRepository.save(object);
    }

    @Override
    public Map<String, String> delete(String id) {

        // Total count of data before delete
        long beforeDelete = mongoLogsRepository.count();

        mongoLogsRepository.deleteById(id);

        // Total count of data after delete
        long afterDelete = mongoLogsRepository.count();

        String messageValue = beforeDelete == afterDelete ? "Something went wrong!" : "Record deleted";

        Map<String, String> deleteMap = new HashMap<>();
        deleteMap.put("message", messageValue);

        return deleteMap;
    }


    public void updateThisDay () throws InterruptedException {
        logger.info("In processe _________________________");

        long time =  10;
        TimeUnit.SECONDS.sleep(time);

        logger.info("FINISH ######################################__");

    }




}
