package com.devsuperior.hrprocess.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
public class LogsForDayTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private LogsForDay logsForDayId;
    private LocalDateTime PeriodStart;
    private LocalDateTime PeriodEnd;
    private String Status;
    private String NumRequests;
    private LocalDateTime DataEnd;


    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_date", nullable = false)
    private Date lastUpdatedDate;

    public LogsForDayTime(){

    }

}
