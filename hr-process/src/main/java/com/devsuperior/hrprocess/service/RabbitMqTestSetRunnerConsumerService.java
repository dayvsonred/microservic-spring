package com.devsuperior.hrprocess.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqTestSetRunnerConsumerService {
    private static Logger log = LoggerFactory.getLogger(RabbitQueueServiceImpl.class);

//    @Autowired
//    //private RegressionRunnerService regressionRunnerService;

//    @Value("${worker.rabbitmq.exchange}")
//    private String exchange;
//
//    @Value("${worker.rabbitmq.routingkey}")
//    private String routingkey;

    @RabbitListener(id = "test.processor.exchange",queues = {"test.processor.queue"},concurrency = "2")
    public void receiver(Long testSetId) {
        log.info("received Message from rabbit : " + testSetId);
        try {
            //this.regressionRunnerService.runRegressionTestSet(testSetId);
            log.info("completed " + testSetId + " task");
        } catch (Exception e) {
            log.error("Error on running test set");
            log.error("Error message : " + ExceptionUtils.getMessage(e));
            log.error("Error trace : " + ExceptionUtils.getStackTrace(e));
        }
    }
}
