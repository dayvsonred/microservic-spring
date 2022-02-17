package com.devsuperior.hrprocess.dto;

import com.devsuperior.hrprocess.entities.LogsForDayTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsForDayRabbitDTO implements Serializable {

    private Long id;
    private String logProcessedData;
    private Boolean finishProcess;
    private List<LogsForDayTime> logsForDayTime;

}
