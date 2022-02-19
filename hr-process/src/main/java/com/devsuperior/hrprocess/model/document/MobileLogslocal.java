package com.devsuperior.hrprocess.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "mobileLogs")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MobileLogslocal {

    @Id
    private String _id;
    private String idApp;
    private Object request;
    private String methodFullName;
    private Object response;
    private String requestDate;
    private String methodShortName;
    private Object ids;
    private Object authentication;

}
