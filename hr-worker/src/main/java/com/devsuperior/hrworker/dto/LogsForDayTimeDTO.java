package com.devsuperior.hrworker.dto;


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
    private LocalDateTime PeriodStart;
    private LocalDateTime PeriodEnd;
    private String Status;
    private String NumRequests;
    private LocalDateTime DataEnd;

}
