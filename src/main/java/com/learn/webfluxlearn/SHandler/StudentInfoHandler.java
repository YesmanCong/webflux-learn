package com.learn.webfluxlearn.SHandler;


import ch.qos.logback.core.rolling.helper.MonoTypedConverter;
import com.github.javafaker.Faker;
import com.learn.webfluxlearn.dao.StudentRepository;
import com.learn.webfluxlearn.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.*;
import reactor.core.scheduler.Scheduler;

import java.util.Random;
import java.util.stream.*;

@Component
@Slf4j
public class StudentInfoHandler {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public Mono<Student> getStudentInfo(String id) {
        return studentRepository.findById(id).defaultIfEmpty(new Student());
    }

    public Flux<Student> listStudentByMathScoreRangeUseAll(int start, int end) {
        return studentRepository.queryStudentByMathScore(start, end);
    }

    public Flux<Student> listStudentByMathScoreRangeUseTemplate(int start, int end) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("mathScore").gte(start),
                Criteria.where("mathScore").lte(end)
        ));
        return mongoTemplate.find(query, Student.class, "student");
    }

    public Mono<Student> randomStudentQuery() {
        Random random = new Random();
        int mathScore = random.nextInt(100);
        int chineseScore = random.nextInt(100);
        int englishScore = random.nextInt(100);
        return mongoTemplate.findOne(new Query().addCriteria(
                        new Criteria().andOperator(
                                Criteria.where("mathScore").is(mathScore),
                                Criteria.where("englishScore").is(englishScore),
                                Criteria.where("chineseScore").is(chineseScore)
                        )
                )
                , Student.class);
    }

    public Mono<Void> createMockStudent(int number) {
        Faker faker = new Faker();
        Flux<Student> studentFlux = Flux.range(0, number).map(i -> getFakerStudent(faker));
        return studentFlux
                .buffer(10000)
                .parallel()
                .flatMap(students -> {
                    log.info("触发插入操作：{}", students.size());
                    return mongoTemplate.insertAll(students).then();
                }).then();
    }


    private Student getFakerStudent(Faker faker) {
        Student student = new Student();
        student.setName(faker.name().fullName());
        student.setAge(faker.number().numberBetween(1, 100));
        student.setSex(faker.demographic().sex());
        student.setStuNO(faker.number().digits(20));
        student.setChineseScore(faker.number().numberBetween(0, 100));
        student.setEnglishScore(faker.number().numberBetween(0, 100));
        student.setMathScore(faker.number().numberBetween(0, 100));
        return student;
    }
}
