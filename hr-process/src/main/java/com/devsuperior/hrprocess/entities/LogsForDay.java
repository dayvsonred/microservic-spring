package com.devsuperior.hrprocess.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "logs_for_day")
public class LogsForDay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private LocalDate logProcessedData;
    private Boolean finishProcess;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "logsForDayId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogsForDayTime> logsForDayTime;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    public LogsForDay(){}


}
