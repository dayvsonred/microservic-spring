package com.devsuperior.hrprocess.service;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
import com.devsuperior.hrprocess.dto.LogsForDayRabbitDTO;
import com.devsuperior.hrprocess.dto.LogsForDayTimeRabbitDTO;
import com.devsuperior.hrprocess.entities.LogsForDay;
import com.devsuperior.hrprocess.entities.LogsForDayTime;
import com.devsuperior.hrprocess.model.document.MobileLogs;
import com.devsuperior.hrprocess.repository.LogsForDayRepository;
import com.devsuperior.hrprocess.repository.LogsForDayTimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class MetricsLogsService {
    private static Logger log = LoggerFactory.getLogger(MetricsLogsService.class);

    @Autowired
    private LogsForDayRepository logsForDayRepository;

    @Autowired
    private LogsForDayTimeRepository logsForDayTimeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MetricMongoStartLogDayService metricMongoStartLogDayService;

    @Autowired
    private MongoLogsServiceImpl mongoLogsServiceImpl;

    private final String StatusStartProcess = "PRONTO";
    private final String StatusConcludeProcess = "CONCLUDE";



    public void processStarted(LogsForDayRabbitDTO logsForDayRabbitDTO) throws JsonProcessingException {
        log.info("processStarted get list of times scan log of this day in message");
        this.showLogProcess("processStarted", "Start");
        try {
            LocalDate dateGet = this.getDueDateByString(logsForDayRabbitDTO.getLogProcessedData());

            if(BooleanUtils.isFalse(this.existOrderStartGetLog(dateGet))){
                LogsForDay startLog = new LogsForDay();
                startLog.setFinishProcess(false);
                startLog.setLogProcessedData(dateGet);
                startLog.setLogsForDayTime(this.generate24HourDay(logsForDayRabbitDTO));
                startLog.setCreatedDate(LocalDateTime.now());
                LogsForDay saveStartLog = logsForDayRepository.save(startLog);
                Long idlist  = saveStartLog.getId();
                //this.getLogInTimeOfDay(saveStartLog.getId());
                this.goLogInTimeOfDay(startLog);
            }
        } catch (Exception e){
            log.info("Error *******************************************************************");
            log.error(e.getMessage(), e);
        }
        this.showLogProcess("processStarted", "Finish");
    }

    private void goLogInTimeOfDay(LogsForDay startLog){
        this.showLogProcess("getLogInTimeOfDay", "Start");
        LocalDate nowDate = LocalDate.now();
        try {
            metricMongoStartLogDayService.send(startLog.getLogsForDayTime().get(0));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.showLogProcess("getLogInTimeOfDay", "Finish");
    }


    private List<LogsForDayTime> generate24HourDay(LogsForDayRabbitDTO logsForDayRabbitDTO) throws RuntimeException{
        try {

            LocalDate dateGet = this.getDueDateByString(logsForDayRabbitDTO.getLogProcessedData());
            String month = dateGet.getMonthValue() >10 ? dateGet.getMonthValue()+"" : "0"+dateGet.getMonthValue();
            String day = dateGet.getDayOfMonth() >10 ? dateGet.getDayOfMonth()+"" : "0"+dateGet.getDayOfMonth();
            LocalDateTime dateTimeGet = this.getDateTimeByString(dateGet.getYear()+"-"+month+"-"+day+" 00:00:00" );
            LocalDateTime dateTimeEndGet = dateTimeGet.plusHours(1);

            LogsForDayTime startTimeLog = new LogsForDayTime();
            startTimeLog.setStatus(StatusStartProcess);
            startTimeLog.setStart(false);
            startTimeLog.setPeriodStart(dateTimeGet);
            startTimeLog.setPeriodEnd(dateTimeEndGet);
            startTimeLog.setOrden(1l);
            startTimeLog.setDataEnd(null);
            startTimeLog.setNumRequests(null);
            startTimeLog.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

            List<LogsForDayTime> listStartLogs = new ArrayList<>();
            listStartLogs.add(startTimeLog);

            for (int i = 1; i <= 23; i++) {
                System.out.println(i);

                LocalDateTime dateTimeGetLoop = this.getDateTimeByString(dateGet.getYear()+"-"+month+"-"+day+" 00:00:00" );
                LocalDateTime dateTimeEndGetLoop = dateTimeGetLoop.plusHours(i+1);
                LocalDateTime dateTimeStartGetLoop = dateTimeGetLoop.plusHours(i);

                LogsForDayTime startTimeLogLoop = new LogsForDayTime();

                startTimeLogLoop.setPeriodStart(dateTimeStartGetLoop);
                startTimeLogLoop.setPeriodEnd(dateTimeEndGetLoop);
                startTimeLogLoop.setOrden(Long.valueOf(i));
                startTimeLogLoop.setStatus(StatusStartProcess);
                startTimeLogLoop.setStart(false);
                startTimeLogLoop.setDataEnd(null);
                startTimeLogLoop.setNumRequests(null);
                startTimeLogLoop.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

                listStartLogs.add(startTimeLogLoop);
            }
            return listStartLogs;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException();
    }


    /**
     * verifica se exist ordem para buscar logs para uma data
     * */
    private Boolean existOrderStartGetLog(LocalDate orderDate) throws RuntimeException{
        this.showLogProcess("existOrderStartGetLog", "Start");
        try {
            LogsForDay logsForDayDTO = logsForDayRepository.findByLogProcessedData(orderDate);
            if(isNull(logsForDayDTO)){
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException();
    }


    /**
     * busca logs por hora de um dia
     * */
    @Transactional
    private void getLogInTimeOfDay(Long idDateProcess){
        this.showLogProcess("getLogInTimeOfDay", "Start");
        LocalDate nowDate = LocalDate.now();
        try {
            LogsForDay logsForDayDTO = logsForDayRepository.findById(idDateProcess).orElseThrow(() -> new RuntimeException());
            /**
             * 0 mais tarde fazer esse da insert em outra lista com o dados para chamada do proximo metodo
             * 1 get mongo doc from time by hour
             * 1.2 salve all doc return on mongo local
             * 2 record salve in local postgre this time and add new time for get process
             * */
            Optional<LogsForDayTime> lofProcess = logsForDayDTO.getLogsForDayTime().stream().filter(ob ->
                    ob.getStatus().toString().equals(StatusStartProcess) &&
                            isNull(ob.getDataEnd()) &&
                            BooleanUtils.isFalse(ob.isStart())).findFirst();

            if(lofProcess.isPresent()){
                this.showLogProcess("getLogInTimeOfDay", " Send MSG get more logs ++++ ");
                LogsForDayTime lofProcessSend = lofProcess.get();
                metricMongoStartLogDayService.send(lofProcessSend);
            }else{
                this.showLogProcess("getLogInTimeOfDay", " Not find more times for get los ******************************* ");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.showLogProcess("getLogInTimeOfDay", "Finish");
    }

    public void getFIndTimeOfDay(LogsForDayRabbitDTO logsForDayRabbitDTO) throws RuntimeException{
        try {
            this.showLogProcess("getFIndTimeOfDay", "Start");
            LocalDate dateGet = this.getDueDateByString(logsForDayRabbitDTO.getLogProcessedData());
            LogsForDay logsForDay = logsForDayRepository.findByLogProcessedData(dateGet);
            if(isNull(logsForDay)){
                this.showLogProcess("getFIndTimeOfDay", " Not exist this Day preProcess ******************************* ");
            }else{
                this.getLogInTimeOfDay(logsForDay.getId());
            }
            this.showLogProcess("getFIndTimeOfDay", "Finish");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 1 get mongo doc from time by hour
     * 1.2 salve all document in file
     * 2 add new get im process read mongo
     * 3 record salve in local postgre this time and add new time for get process
     * */
    public void controlLogByTime(LogsForDayTimeRabbitDTO logsForDayTimeRabbitDTO) throws RuntimeException{
        this.showLogProcess("controlLogByTime", "Start");
        try {
            LogsForDayTime logsForDayTime = logsForDayTimeRepository.findById(logsForDayTimeRabbitDTO.getId()).orElseThrow(() -> new RuntimeException());
            logsForDayTime.setStart(true);
            logsForDayTime = logsForDayTimeRepository.save(logsForDayTime);

            List<MobileLogs> mobileLogs = this.getMongoLogByTimeFromDay(logsForDayTime.getPeriodStart(), logsForDayTime.getPeriodEnd());

            this.saveFileLogsByDate(mobileLogs,logsForDayTime);

            /** falta add na fila para iniciar ler aquivo e gerista suces no postigre */

            logsForDayTime.setStatus(StatusConcludeProcess);
            logsForDayTime.setDataEnd(LocalDateTime.now());
            logsForDayTime.setNumRequests(Long.valueOf(mobileLogs.size()));
            logsForDayTimeRepository.save(logsForDayTime);

            /** flush memo */
            mobileLogs = new ArrayList<>();

            this.getLogInTimeOfDay(logsForDayTime.getLogsForDay().getId());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.showLogProcess("controlLogByTime", "Finish");
    }


    private List<MobileLogs> getMongoLogByTimeFromDay(LocalDateTime dateStart,LocalDateTime dateEnd) throws RuntimeException{
        try {
            this.showLogProcess("getMongoLogByTimeFromDay", "Start");
            ZoneId zoneId = ZoneId.of("America/Sao_Paulo");  //Zone information
            ZonedDateTime dateStartZone = dateStart.atZone(zoneId);
            ZonedDateTime dateEndZone = dateEnd.atZone(zoneId);
            List<MobileLogs> mobileLogs =  mongoLogsServiceImpl.getLogByInterval222(dateStartZone,dateEndZone);
            this.showLogProcess("getMongoLogByTimeFromDay", "Finish");
            return mobileLogs;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException();
    }


    private void saveFileLogsByDate(List<MobileLogs> mobileLogs, LogsForDayTime logsForDayTime) throws IOException, RuntimeException {
        try {
            ObjectMapper Obj = new ObjectMapper();
            String jsonStr = Obj.writeValueAsString(mobileLogs);

            LocalDateTime start = logsForDayTime.getPeriodStart();
            LocalDateTime end = logsForDayTime.getPeriodEnd();
            String statDateName = start.getYear()+"_"+start.getMonthValue()+"_"+start.getDayOfMonth()+" H"+start.getHour();
            String endDateName = end.getYear()+"_"+end.getMonthValue()+"_"+end.getDayOfMonth()+" H"+end.getHour();
            String nameFile = "mongo_prod_"+ statDateName +" - "+endDateName;

            File file = new File("H:\\\\DADOS_LOGS\\"+nameFile+".txt");
            if(BooleanUtils.isFalse(file.exists())){
                System.out.println("File Not Exist... created file"+ nameFile);
                FileWriter fw = new FileWriter(file);
                fw.write(jsonStr);
                fw.flush();
                fw.close();
                file.createNewFile();
            }
            if(file.exists()){
                jsonStr = null;
                System.out.println("File Created OK "+ nameFile);
            }
        } catch (IOException e) {
            System.out.println("Erro File ");
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private LocalDate getDueDateByString(String date){
        LocalDate nowDate = LocalDate.now();
        try {
            if (isNull(date)) {
                date = LocalDate.now().toString();
                log.error("error date is null ************************************");
            }
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d");
            nowDate =  LocalDate.parse(date, format);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return nowDate;
    }

    private LocalDateTime getDateTimeByString(String date) throws RuntimeException{
        try {
            if (isNull(date)) {
                log.error("error date is null ************************************");
                throw new RuntimeException();
            }
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(date, format);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
       throw new RuntimeException();
    }


    private void showLogProcess(String Process, String status){
        LocalDateTime nowTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        log.info("------>"+ Process +" "+ status +" ----> "+ nowTime );
    }

}
