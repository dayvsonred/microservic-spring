package com.devsuperior.hrprocess.service;

import com.devsuperior.hrprocess.model.document.MobileLogs;
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
