package com.devsuperior.hrprocess.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class LogsForDayTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private LogsForDay logsForDayId;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private String status;
    private String numRequests;
    private LocalDateTime DataEnd;


    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;

    public LogsForDayTime(){

    }

}
