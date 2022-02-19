package com.devsuperior.hrprocess.consumer;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
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
public class MetricMongoFindLogDayConsumer {
    private static Logger log = LoggerFactory.getLogger(MetricMongoFindLogDayConsumer.class);

    @Autowired
    private MetricsLogsService metricsLogsService;

    @RabbitListener(id = "${api.rabbitmq.find.log.all.day.exchange}",queues = {"${api.rabbitmq.find.log.all.day.queue}"},concurrency = "1")
    public void receiver(LogsForDayRabbitDTO objectRabbit) {
        log.info("received Message from --------------- metrics.mongo.find.log.all.queue " );
        try {
            this.metricsLogsService.getFIndTimeOfDay(objectRabbit);
        } catch (Exception e) {
            log.error("Error on running test set MetricMongoStartLogDayConsumer");
            log.error("Error message : " + ExceptionUtils.getMessage(e));
            log.error("Error trace : " + ExceptionUtils.getStackTrace(e));
        }
    }
}
