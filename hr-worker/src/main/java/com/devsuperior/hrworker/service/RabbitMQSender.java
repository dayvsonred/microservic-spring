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

    public void send(UpdateLogsMongoDTO menDto) {
        rabbitTemplate.convertAndSend(exchange, routingkey, menDto);
        System.out.println("Send msg = " + menDto);

    }


//    private final RabbitTemplate rabbitTemplate;
//    private final ObjectMapper objectMapper;
//
//    @Value("${api.queue.user.device.routing}")
//    private String deviceRouting;
//
//    @Value("${api.queue.user.device.exchange}")
//    private String deviceExchange;

//    public DeviceProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public void sendMessageSilently(String cpf, Boolean verified, DeviceRequestDTO deviceRequestDTO) {
//        try {
//            MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
//            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(new DeviceDTO(cpf, verified, deviceRequestDTO))).andProperties(props).build();
//            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//            rabbitTemplate.setExchange(deviceExchange);
//            rabbitTemplate.setRoutingKey(deviceRouting);
//            rabbitTemplate.convertAndSend(message);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//    }
//
//    public void sendMessage(String cpf, Boolean verified, DeviceRequestDTO deviceRequestDTO) throws JsonProcessingException {
//        MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
//        Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(new DeviceDTO(cpf, verified, deviceRequestDTO))).andProperties(props).build();
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        rabbitTemplate.setExchange(deviceExchange);
//        rabbitTemplate.setRoutingKey(deviceRouting);
//        rabbitTemplate.convertAndSend(message);
//    }


}
