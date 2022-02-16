package com.devsuperior.hrprocess.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsForDayTimeRabbitDTO implements Serializable {

    private Long id;
    private boolean start;
    private Long orden;
    private String periodStart;
    private String periodEnd;
    private String status;
    private Long numRequests;
    private LocalDateTime dataEnd;
}
