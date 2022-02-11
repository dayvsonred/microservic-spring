package com.devsuperior.hrprocess.queues;

import com.devsuperior.hrprocess.service.RabbitQueueServiceImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestProcessQueue {

//    @Autowired
//    private RabbitQueueServiceImpl rabbitQueueServiceImpl;
//
//    @Bean
//    public void creatTestProcessQueue(){
//        rabbitQueueServiceImpl.addNewQueue("teste_process_queue","teste_process_exchage","teste_process_key");
//    }

    static final String topicExchangeName = "test.processor.exchange";

    static final String queueName = "test.processor.queue";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#");
    }

}
