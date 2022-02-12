package com.devsuperior.hrprocess.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "logs_for_day")
public class LogsForDay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private LocalDate LogProcessedData;
    private Boolean FinishProcess;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "logsForDayId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogsForDayTime> logsForDayTime;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_date", nullable = false)
    private Date lastUpdatedDate;

    public LogsForDay(){}


}
