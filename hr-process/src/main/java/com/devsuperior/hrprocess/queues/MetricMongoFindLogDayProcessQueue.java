package com.devsuperior.hrprocess.queues;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricMongoFindLogDayProcessQueue {

    @Value("${api.rabbitmq.find.log.all.day.exchange}")
    private String topicExchangeName;

    @Value("${api.rabbitmq.find.log.all.day.key}")
    private String key;

    @Value("${api.rabbitmq.find.log.all.day.queue}")
    private String queueName;

    @Bean
    Queue queueMetricsFindLogDay() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchangeMetricsFindLogDay() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding bindingMetricsFindLogDay(Queue queueMetricsFindLogDay, TopicExchange exchangeMetricsFindLogDay) {
        return BindingBuilder.bind(queueMetricsFindLogDay).to(exchangeMetricsFindLogDay).with("#");
    }
}
