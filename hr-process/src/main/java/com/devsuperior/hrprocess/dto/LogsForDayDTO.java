package com.devsuperior.hrprocess.dto;

import com.devsuperior.hrprocess.entities.LogsForDayTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsForDayDTO implements Serializable {

    private String LogProcessedData;
    private Boolean FinishProcess;
    private List<LogsForDayTime> logsForDayTime;

}
