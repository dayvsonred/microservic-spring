package com.devsuperior.hrworker.service;

import com.devsuperior.hrworker.model.MobileLogs;
import com.devsuperior.hrworker.repository.MongoLogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

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

    public LocalDate getDueDateByString(String date){
        LocalDate nowDate = LocalDate.now();
        try {
            if (isNull(date)) {
                date = LocalDate.now().toString();
                logger.error("error date is null ************************************");
            }
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            nowDate =  LocalDate.parse(date, format);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return nowDate;
    }




}
