package com.devsuperior.hrprocess.service;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
import com.devsuperior.hrprocess.dto.LogsForDayRabbitDTO;
import com.devsuperior.hrprocess.entities.LogsForDay;
import com.devsuperior.hrprocess.repository.LogsForDayRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;

@Service
public class MetricsLogsService {
    private static Logger log = LoggerFactory.getLogger(MetricsLogsService.class);

    @Autowired
    private LogsForDayRepository logsForDayRepository;

//    private final ObjectMapper mapper = new ObjectMapper();

    public void processStarted(LogsForDayRabbitDTO logsForDayRabbitDTO) throws JsonProcessingException {
        try {
            log.info("processStarted get list of times scan log of this day in message");


            LocalDate buscar = this.getDueDateByString(logsForDayRabbitDTO.getLogProcessedData());

            LogsForDay logsForDayDTO = logsForDayRepository.findByLogProcessedData(buscar);
            if(isNull(logsForDayDTO)){


                LogsForDay startLog = new LogsForDay();
                startLog.setFinishProcess(false);
                startLog.setLogProcessedData(buscar);
                startLog.setLogsForDayTime(null);
                startLog.setCreatedDate(LocalDateTime.now());
                logsForDayRepository.save(startLog);

            }
//

//            Object deserializedlogsForDayDTO =  SerializationUtils.deserialize(message.getBody());
//            LogsForDayDTO ssssssss =  (LogsForDayDTO) SerializationUtils.deserialize(message.getBody());
            //LogsForDayDTO logsForDayDTO = mapper.readValue(new String(message.getBody(), StandardCharsets.UTF_8), LogsForDayDTO.class);

            log.info("received Message okokokokokokokok : " );

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }



    }


    private LocalDate getDueDateByString(String date){
        LocalDate nowDate = LocalDate.now();
        try {
            if (isNull(date)) {
                date = LocalDate.now().toString();
                log.error("error date is null ************************************");
            }
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d");
            nowDate =  LocalDate.parse(date, format);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return nowDate;
    }

}
