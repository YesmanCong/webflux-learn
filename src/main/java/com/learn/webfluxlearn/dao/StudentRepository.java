package com.learn.webfluxlearn.dao;

import com.learn.webfluxlearn.domain.Student;
import org.springframework.data.mongodb.repository.*;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface StudentRepository extends ReactiveMongoRepository<Student, String>, ReactiveCrudRepository<Student,String> {


    @Query("{ $and:[ {'mathScore':{$gte:?0}},{'mathScore':{$lte:?1}} ] }")
    Flux<Student> queryStudentByMathScore(int start,int end);
}
