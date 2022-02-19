package com.devsuperior.hrprocess.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomMongoLogCollection {

    @Value("${api.data.mongodb.collection}")
    private String collection;

    @Value("api.data.mongodb.collection")
    private String collection333;

    public String getCollectionName() {
        return this.collection;
    }

}
