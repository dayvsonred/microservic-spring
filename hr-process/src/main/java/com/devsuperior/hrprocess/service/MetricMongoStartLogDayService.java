package com.devsuperior.hrprocess.service;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
import com.devsuperior.hrprocess.dto.LogsForDayTimeDTO;
import com.devsuperior.hrprocess.dto.LogsForDayTimeRabbitDTO;
import com.devsuperior.hrprocess.entities.LogsForDayTime;
import com.devsuperior.hrprocess.repository.LogsForDayRepository;
import com.devsuperior.hrprocess.repository.LogsForDayTimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class MetricMongoStartLogDayService {

    private static Logger log = LoggerFactory.getLogger(MetricsLogsService.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private ObjectMapper objectMapper;

    @Value("${api.rabbitmq.start.log.all.day.exchange}")
    private String exchange;

    @Value("${api.rabbitmq.start.log.all.day.routingkey}")
    private String routingkey;

    @Value("${api.rabbitmq.start.log.all.day.queue}")
    private String queue;

    public void send(LogsForDayTime logsForDayTime){
        try{
            System.out.println("------>Send RABBIT MetricMongoStartLogDayService");
            rabbitTemplate.convertAndSend(exchange, routingkey, LogsForDayTimeRabbitDTO.builder()
                    .id(logsForDayTime.getId())
                            .periodStart(logsForDayTime.getPeriodStart().toString())
                            .periodEnd(logsForDayTime.getPeriodEnd().toString())
                    .build());
            System.out.println("------> Finish MetricMongoStartLogDayService");
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
