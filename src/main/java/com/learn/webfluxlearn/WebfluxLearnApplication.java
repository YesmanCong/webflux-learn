package com.learn.webfluxlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class WebfluxLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxLearnApplication.class, args);
    }

}
