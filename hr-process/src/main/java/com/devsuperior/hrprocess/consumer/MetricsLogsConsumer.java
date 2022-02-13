package com.devsuperior.hrprocess.consumer;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
import com.devsuperior.hrprocess.dto.LogsForDayRabbitDTO;
import com.devsuperior.hrprocess.service.MetricsLogsService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricsLogsConsumer {
    private static Logger log = LoggerFactory.getLogger(MetricsLogsConsumer.class);

    @Autowired
    private MetricsLogsService metricsLogsService;

    @RabbitListener(id = "metrics.mongo.log.update.exchange",queues = {"metrics.mongo.log.update.queue"},concurrency = "2")
    public void receiver(LogsForDayRabbitDTO logsForDayDTO) {
        log.info("received Message from rabbit  metrics.mongo.log.update.queue " );
        try {
            this.metricsLogsService.processStarted(logsForDayDTO);
            log.info("completed task");
        } catch (Exception e) {
            log.error("Error on running test set");
            log.error("Error message : " + ExceptionUtils.getMessage(e));
            log.error("Error trace : " + ExceptionUtils.getStackTrace(e));
        }
    }
}
