package com.learn.webfluxlearn.SHandler;


import com.github.javafaker.Faker;
import com.learn.webfluxlearn.dao.StudentRepository;
import com.learn.webfluxlearn.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.*;

@Component
public class StudentInfoHandler {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public Mono<Student> getStudentInfo(String id){
        return studentRepository.findById(id).defaultIfEmpty(new Student());
    }

    public Flux<Student> listStudentByMathScoreRangeUseAll(int start, int end){
        return studentRepository.queryStudentByMathScore(start, end);
    }

    public Flux<Student> listStudentByMathScoreRangeUseTemplate(int start, int end){
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("mathScore").gte(start),
                Criteria.where("mathScore").lte(end)
        ));
        return mongoTemplate.find(query, Student.class, "student");
    }

    public Mono<Void> createMockStudent(int number){
        Faker faker = new Faker();

        return studentRepository.saveAll(Flux.range(1, number).map(i -> {
            Student student = new Student();
            student.setName(faker.name().fullName());
            student.setAge(faker.number().numberBetween(18, 30));
            student.setSex(faker.demographic().sex());
            student.setStuNO(faker.number().digits(10));
            student.setChineseScore(faker.number().numberBetween(0, 100));
            student.setEnglishScore(faker.number().numberBetween(0,100));
            student.setMathScore(faker.number().numberBetween(0,100));
            return student;
        })).then();

    }

}
