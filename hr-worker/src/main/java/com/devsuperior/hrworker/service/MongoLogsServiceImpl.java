package com.devsuperior.hrworker.service;

import com.devsuperior.hrworker.model.MobileLogs;
import com.devsuperior.hrworker.model.MongoLogsRequestModel;
import com.devsuperior.hrworker.repository.MongoLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MongoLogsServiceImpl implements MongoLogsService {

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

}
