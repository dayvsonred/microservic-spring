package com.devsuperior.hrworker.service;

import com.devsuperior.hrworker.dto.LogsForDayDTO;
import com.devsuperior.hrworker.resources.WorkerResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMQSenderLogsMongo {

    private static Logger logger = LoggerFactory.getLogger(WorkerResource.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private ObjectMapper objectMapper;

    @Value("${worker.rabbitmq.mongolog.exchange}")
    private String exchange;

    @Value("${worker.rabbitmq.mongolog.routingkey}")
    private String routingkey;

    @Value("${worker.rabbitmq.mongolog.queue}")
    private String queue;

    public void send(LogsForDayDTO logsForDayDTO) throws JsonProcessingException {
        try{
            System.out.println("Send For RABBIT DTO = " + logsForDayDTO.getLogProcessedData());
            //System.out.println("Send For RABBIT DTO = " + logsForDayDTO.getFinishProcess());
            MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();

            Map<String, Object> args = new HashMap<>();
            args.put("LogProcessedData", logsForDayDTO.getLogProcessedData());

            byte[] bytesOflogsForDayDTO = SerializationUtils.serialize(args);

            //Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(logsForDayDTO)).andProperties(props).build();
            Message message = MessageBuilder.withBody(bytesOflogsForDayDTO).andProperties(props).build();

            rabbitTemplate.convertAndSend(exchange, routingkey, logsForDayDTO);
            System.out.println("Send logsForDayDTO  msg = " + logsForDayDTO.getLogProcessedData());
        }catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
    }

}
