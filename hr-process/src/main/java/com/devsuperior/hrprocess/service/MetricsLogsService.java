package com.devsuperior.hrprocess.service;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.nio.charset.StandardCharsets;

@Service
public class MetricsLogsService {
    private static Logger log = LoggerFactory.getLogger(MetricsLogsService.class);

//    private final ObjectMapper mapper = new ObjectMapper();

    public void processStarted(LogsForDayDTO logsForDayDTO) throws JsonProcessingException {
        try {
            log.info("processStarted get list of times scan log of this day in message");



//            Object deserializedlogsForDayDTO =  SerializationUtils.deserialize(message.getBody());
//
//            LogsForDayDTO ssssssss =  (LogsForDayDTO) SerializationUtils.deserialize(message.getBody());

            //LogsForDayDTO logsForDayDTO = mapper.readValue(new String(message.getBody(), StandardCharsets.UTF_8), LogsForDayDTO.class);

            log.info("received Message okokokokokokokok : " );

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }



    }

}
