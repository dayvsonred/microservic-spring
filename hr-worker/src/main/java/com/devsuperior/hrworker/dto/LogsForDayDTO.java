package com.devsuperior.hrworker.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Builder
@ToString
public class LogsForDayDTO implements Serializable {

    private String logProcessedData;
    private Boolean FinishProcess;

    public LogsForDayDTO(String logProcessedData, Boolean finishProcess) {
        this.logProcessedData = logProcessedData;
        FinishProcess = finishProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogsForDayDTO)) return false;
        LogsForDayDTO that = (LogsForDayDTO) o;
        return getLogProcessedData().equals(that.getLogProcessedData()) && getFinishProcess().equals(that.getFinishProcess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogProcessedData(), getFinishProcess());
    }

    public LogsForDayDTO (){

    }
}
