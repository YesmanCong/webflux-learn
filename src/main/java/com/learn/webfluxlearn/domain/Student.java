package com.learn.webfluxlearn.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("student")
public class Student {

    @Id
    private String id;

    private String name;

    private int age;

    private String sex;

    private String stuNO;

    private Integer chineseScore;

    private Integer englishScore;

    private Integer mathScore;

    private Double avgScore;

}
