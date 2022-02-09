package com.devsuperior.hrworker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.format.DateTimeFormatter;


@Document
@Getter
@AllArgsConstructor
public class MongoLogsRequestModel {

    @Id
    private String _id;
    private String idApp;
    private Object request;
    private String methodFullName;
    private Object response;
    private DateTimeFormatter requestDate;
    private String methodShortName;
    private Object ids;
    private Object authentication;

    public MongoLogsRequestModel(){

    }

}
