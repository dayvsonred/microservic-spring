package com.devsuperior.hrprocess.queues;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricMongoStartLogDayProcessQueue {

    @Value("${api.rabbitmq.start.log.all.day.exchange}")
    private String topicExchangeName;

    @Value("${api.rabbitmq.start.log.all.day.routingkey}")
    private String routingkey;

    @Value("${api.rabbitmq.start.log.all.day.queue}")
    private String queueName;

    @Bean
    Queue queueMetricsStartLogDay() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchangeMetricsStartLogDay() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding bindingMetricsStartLogDay(Queue queueMetricsStartLogDay, TopicExchange exchangeMetricsStartLogDay) {
        return BindingBuilder.bind(queueMetricsStartLogDay).to(exchangeMetricsStartLogDay).with("#");
    }
}
