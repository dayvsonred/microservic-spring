package com.devsuperior.hrworker.service;

import com.devsuperior.hrworker.model.MobileLogs;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MongoLogsService {

    MobileLogs create(MobileLogs mobileLogs);

    List<MobileLogs> read();

    MobileLogs update(MobileLogs mobileLogs);

    Map<String, String> delete(String id);


}
