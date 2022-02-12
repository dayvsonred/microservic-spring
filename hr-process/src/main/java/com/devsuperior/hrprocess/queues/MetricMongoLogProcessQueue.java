package com.devsuperior.hrprocess.queues;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricMongoLogProcessQueue {

    static final String topicExchangeName = "metrics.mongo.log.update.exchange";

    static final String queueName = "metrics.mongo.log.update.queue";

    @Bean
    Queue queueMetrics() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchangeMetrics() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding bindingMetrics(Queue queueMetrics, TopicExchange exchangeMetrics) {
        return BindingBuilder.bind(queueMetrics).to(exchangeMetrics).with("#");
    }

}
