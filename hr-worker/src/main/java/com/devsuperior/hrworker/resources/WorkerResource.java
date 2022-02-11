package com.devsuperior.hrworker.resources;

import com.devsuperior.hrworker.dto.UpdateLogsMongoDTO;
import com.devsuperior.hrworker.entities.Worker;
import com.devsuperior.hrworker.model.MobileLogs;
import com.devsuperior.hrworker.repository.WorkerRepository;
import com.devsuperior.hrworker.service.MongoLogsService;
import com.devsuperior.hrworker.service.MongoLogsServiceImpl;
import com.devsuperior.hrworker.service.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

    private static Logger logger = LoggerFactory.getLogger(WorkerResource.class);

    @Value("${test.config}")
    private String testConfig;

    @Autowired
    private Environment env;

    @Autowired
    private WorkerRepository repository;

    @Autowired
    private MongoLogsService mongoLogsService;

    @Autowired
    private MongoLogsServiceImpl mongoLogsServiceImpl;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @GetMapping(value = "/configs")
    public ResponseEntity<Void> getConfigs() {
        logger.info("CONFIG = " + testConfig);
        return ResponseEntity.noContent().build();
    }

    /** REQUEST DO MOGO  **/
    @GetMapping(value = "/mongo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MobileLogs>> getAllMongo() {
        logger.info("VEIO MONGO OK _________________________");
        return ResponseEntity.ok().body(this.mongoLogsService.read());
    }

    /** REQUEST DO MOGO  **/
    @GetMapping(value = "/mongo_update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateThisDay() {
        Integer randomWithMathRandom = (int) ((Math.random() * (10000 - 1)) + 10);
        logger.info("updateThisDay _________________________");
        try{
            //this.mongoLogsServiceImpl.updateThisDay();
            //rabbitMQSender.send(UpdateLogsMongoDTO.builder().go("true teste msg okkkkkkkkoasodaoks").build());
            rabbitMQSender.send(randomWithMathRandom.longValue());

        }catch (Exception e){
            logger.info("Exeption ");
        }
        List myObjList = new ArrayList();
        myObjList.add("start update data");
        return ResponseEntity.ok().body(myObjList);
    }

    @GetMapping
    public ResponseEntity<List<Worker>> findAll() {
        List<Worker> list = repository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id) {

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("PORT = " + env.getProperty("local.server.port"));

        Worker obj = repository.findById(id).get();
        return ResponseEntity.ok(obj);
    }
}
