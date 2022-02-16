package com.devsuperior.hrprocess.dto;

import com.devsuperior.hrprocess.entities.LogsForDay;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsForDayTimeDTO implements Serializable {

    private LogsForDayDTO logsForDayId;
    private boolean start;
    private Long orden;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private String status;
    private Long numRequests;
    private LocalDateTime dataEnd;
}
