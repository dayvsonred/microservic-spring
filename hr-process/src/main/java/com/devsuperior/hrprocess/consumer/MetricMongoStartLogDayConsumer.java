package com.devsuperior.hrprocess.consumer;

import com.devsuperior.hrprocess.dto.LogsForDayRabbitDTO;
import com.devsuperior.hrprocess.dto.LogsForDayTimeRabbitDTO;
import com.devsuperior.hrprocess.service.MetricsLogsService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MetricMongoStartLogDayConsumer {
    private static Logger log = LoggerFactory.getLogger(MetricMongoStartLogDayConsumer.class);

    @Autowired
    private MetricsLogsService metricsLogsService;

    /***
     * busca losgs por tempo sempre q tiver um PRONTO NA TABELA
     * */

    @RabbitListener(id = "${api.rabbitmq.start.log.all.day.exchange}",queues = {"${api.rabbitmq.start.log.all.day.queue}"},concurrency = "1")
    public void receiver(LogsForDayTimeRabbitDTO objectRabbit) {
        log.info("received Message from rabbit  metrics.mongo.log.update.queue " );
        try {
            this.metricsLogsService.controlLogByTime(objectRabbit);
            log.info("completed task");
        } catch (Exception e) {
            log.error("Error on running test set MetricMongoStartLogDayConsumer");
            log.error("Error message : " + ExceptionUtils.getMessage(e));
            log.error("Error trace : " + ExceptionUtils.getStackTrace(e));
        }
    }
}
