package com.devsuperior.hrprocess.entities;

import com.devsuperior.hrprocess.dto.LogsForDayDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity(name = "logs_for_day_time")
@Getter
@Setter
@AllArgsConstructor
@Table(name = "logs_for_day_time")
public class LogsForDayTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private LogsForDay logsForDay;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private String status;
    private Long numRequests;
    private LocalDateTime dataEnd;
    private boolean start;
    private Long orden;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public LogsForDayTime(){

    }

}
