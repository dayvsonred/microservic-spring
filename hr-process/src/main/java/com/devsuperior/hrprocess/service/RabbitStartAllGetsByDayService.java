package com.devsuperior.hrprocess.service;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
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
public class RabbitStartAllGetsByDayService {

    private static Logger logger = LoggerFactory.getLogger(RabbitStartAllGetsByDayService.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private ObjectMapper objectMapper;

    @Value("${api.rabbitmq.start.log.all.day.exchange}")
    private String exchange;

    @Value("${api.rabbitmq.start.log.all.day.routingkey}")
    private String routingkey;

    @Value("${api.rabbitmq.start.log.all.day.queue}")
    private String queue;

    public void send(LogsForDayDTO logsForDayDTO) throws JsonProcessingException {
        try{
            System.out.println("--------> Send RABBIT DTO = " + exchange);
//            MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
//            Map<String, Object> args = new HashMap<>();
//            args.put("LogProcessedData", logsForDayDTO.getLogProcessedData());
//            byte[] bytesOflogsForDayDTO = SerializationUtils.serialize(args);
//            //Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(logsForDayDTO)).andProperties(props).build();
//            Message message = MessageBuilder.withBody(bytesOflogsForDayDTO).andProperties(props).build();

            rabbitTemplate.convertAndSend(exchange, routingkey, logsForDayDTO);
            System.out.println("--------> Send Finish = " );
        }catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
    }

}
