package com.devsuperior.hrprocess.service;

import com.devsuperior.hrprocess.dto.LogsForDayRabbitDTO;
import com.devsuperior.hrprocess.entities.LogsForDay;
import com.devsuperior.hrprocess.entities.LogsForDayTime;
import com.devsuperior.hrprocess.repository.LogsForDayRepository;
import com.devsuperior.hrprocess.repository.LogsForDayTimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

//    private final ObjectMapper mapper = new ObjectMapper();
    @Transactional
    public void processStarted(LogsForDayRabbitDTO logsForDayRabbitDTO) throws JsonProcessingException {
        log.info("processStarted get list of times scan log of this day in message");
        this.showLogProcess("processStarted", "Start");
        try {
            LocalDate dateGet = this.getDueDateByString(logsForDayRabbitDTO.getLogProcessedData());
            String month = dateGet.getMonthValue() >10 ? dateGet.getMonthValue()+"" : "0"+dateGet.getMonthValue();
            String day = dateGet.getDayOfMonth() >10 ? dateGet.getDayOfMonth()+"" : "0"+dateGet.getDayOfMonth();
            LocalDateTime dateTimeGet = this.getDateTimeByString(dateGet.getYear()+"-"+month+"-"+day+" 00:00:00" );
            LocalDateTime dateTimeEndGet = dateTimeGet.plusHours(1);


            System.out.println(BooleanUtils.isFalse(this.existOrderStartGetLog(dateGet)));

            if(BooleanUtils.isFalse(this.existOrderStartGetLog(dateGet))){
                LogsForDayTime startTimeLog = new LogsForDayTime();
                startTimeLog.setStatus("PRONTO");
                startTimeLog.setStart(false);
                startTimeLog.setPeriodStart(dateTimeGet);
                startTimeLog.setPeriodEnd(dateTimeEndGet);
                startTimeLog.setOrden(1l);
                startTimeLog.setDataEnd(null);
                startTimeLog.setNumRequests(null);
                startTimeLog.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

                List<LogsForDayTime> listStartLogs = new ArrayList<>();
                listStartLogs.add(startTimeLog);

                LogsForDay startLog = new LogsForDay();
                startLog.setFinishProcess(false);
                startLog.setLogProcessedData(dateGet);
                startLog.setLogsForDayTime(listStartLogs);
                startLog.setCreatedDate(LocalDateTime.now());
                LogsForDay saveStartLog = logsForDayRepository.save(startLog);
                this.getLogInTimeOfDay(saveStartLog.getId());
            }
        } catch (Exception e){
            log.info("Error *******************************************************************");
            log.error(e.getMessage(), e);
        }
        this.showLogProcess("processStarted", "Finish");
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
    private void getLogInTimeOfDay(Long idDateProcess){
        this.showLogProcess("getLogInTimeOfDay", "Start");
        LocalDate nowDate = LocalDate.now();
        try {

//            LogsForDayTime logsNextTime = entityManager
//                    .createQuery("SELECT u FROM logs_for_day_time u WHERE u.logs_for_day_id = :logDayId",LogsForDayTime.class)
//                    .setParameter("logDayId",idDateProcess.toString())
//                    .getSingleResult();
            //LogsForDayTime logsNextTime  = query.setParameter(1,idDateProcess).getSingleResult();
//            LogsForDayTime logsNextTime  = entityManager.createQuery("SELECT u.logs_for_day_id FROM logs_for_day_time u WHERE u.logs_for_day_id = 1",LogsForDayTime.class).getSingleResult();
//            LogsForDayTime logsNextTime =  logsForDayTimeRepository.getLogsTimeNextProcess(idDateProcess.toString());
//            LogsForDay logsForDayDTO = logsForDayRepository.findByLogProcessedData(orderDate);
//            logsForDayDTO.getLogsForDayTime().stream().forEach( logAnyTime -> { });

            LogsForDay logsForDayDTO = logsForDayRepository.findById(idDateProcess).orElseThrow(() -> new RuntimeException());



            /**
             * 1 get mongo doc from time by hour
             * 1.2 salve all doc return on mongo local
             * 2 record salve in local postgre this time and add new time for get process
             * */
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");





        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.showLogProcess("getLogInTimeOfDay", "Finish");
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
