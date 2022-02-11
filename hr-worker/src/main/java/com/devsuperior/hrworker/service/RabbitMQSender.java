package com.devsuperior.hrworker.service;


import com.devsuperior.hrworker.dto.UpdateLogsMongoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${worker.rabbitmq.exchange}")
    private String exchange;

    @Value("${worker.rabbitmq.routingkey}")
    private String routingkey;

    public void send(Long menDto) {
        rabbitTemplate.convertAndSend(exchange, routingkey, menDto);
        System.out.println("Send msg = " + menDto);
    }
}
