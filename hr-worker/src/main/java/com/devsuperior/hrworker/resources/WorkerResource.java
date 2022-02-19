package com.devsuperior.hrworker.resources;

import com.devsuperior.hrworker.dto.LogsForDayDTO;
import com.devsuperior.hrworker.entities.Worker;
import com.devsuperior.hrworker.model.MobileLogs;
import com.devsuperior.hrworker.repository.WorkerRepository;
import com.devsuperior.hrworker.service.MongoLogsService;
import com.devsuperior.hrworker.service.MongoLogsServiceImpl;
import com.devsuperior.hrworker.service.RabbitMQSender;
import com.devsuperior.hrworker.service.RabbitMQSenderLogsMongo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Autowired
    private RabbitMQSenderLogsMongo rabbitMQSenderLogsMongo;

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

    /** REQUEST DO MOGO  TESTE **/
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

    /** REQUEST DO MOGO Start UPDAT DAY **/
    @GetMapping(value = "/mongo_update_day", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity startUpdateThisDay() {
        logger.info("startUpdateThisDay _________________________");
        LocalDate localDate = LocalDate.now();
        logger.info("localDate is = "+ localDate);
        try{
            rabbitMQSenderLogsMongo.send(LogsForDayDTO.builder().logProcessedData(localDate.toString()).FinishProcess(false).build());
        }catch (Exception e){
            logger.info("Exeption ");
        }
        List myObjList = new ArrayList();
        myObjList.add("start update data " + localDate);
        return ResponseEntity.ok().body(myObjList);
    }

    /** PROD REQUEST **/
    @PostMapping(value = "/mongo_update_day_prod",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity startProcessUpdateDay(@RequestBody LogsForDayDTO logsForDayDTO) {
        List myObjList = new ArrayList();
        try{
            LocalDate localDate = mongoLogsServiceImpl.getDueDateByString(logsForDayDTO.getLogProcessedData());
            rabbitMQSenderLogsMongo.send(LogsForDayDTO.builder().logProcessedData(localDate.toString()).FinishProcess(false).build());

            myObjList.add("start update data " + localDate.toString());
        }catch (Exception e){
            logger.info("Exception mongo_update_day_prod");
            logger.error(e.getMessage(), e);
        }
        return ResponseEntity.ok().body(myObjList);
    }

    /** PROD REQUEST **/
    @PostMapping(value = "/mongo_find_day_prod",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity findProcessUpdateDay(@RequestBody LogsForDayDTO logsForDayDTO) {
        List myObjList = new ArrayList();
        try{
            LocalDate localDate = mongoLogsServiceImpl.getDueDateByString(logsForDayDTO.getLogProcessedData());
            rabbitMQSenderLogsMongo.send(LogsForDayDTO.builder().logProcessedData(localDate.toString()).FinishProcess(false).build());
            myObjList.add("send MSG find data " + localDate.toString());
        }catch (Exception e){
            logger.info("Exception mongo_update_day_prod");
            logger.error(e.getMessage(), e);
        }
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
