package com.devsuperior.hrworker.service;

import com.devsuperior.hrworker.dto.LogsForDayDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSenderFindLogsMongo {

    private static Logger logger = LoggerFactory.getLogger(RabbitMQSenderFindLogsMongo.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private ObjectMapper objectMapper;

    @Value("${api.rabbitmq.find.log.all.day.exchange}")
    private String exchange;

    @Value("${api.rabbitmq.find.log.all.day.key}")
    private String routingkey;

    @Value("${api.rabbitmq.find.log.all.day.queue}")
    private String queue;

    public void send(LogsForDayDTO logsForDayDTO) throws JsonProcessingException {
        try{
            System.out.println("Send For RABBIT DTO = " + logsForDayDTO.getLogProcessedData());
            rabbitTemplate.convertAndSend(exchange, routingkey, logsForDayDTO);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
    }

}
