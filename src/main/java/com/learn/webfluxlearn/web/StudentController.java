package com.learn.webfluxlearn.web;


import com.learn.webfluxlearn.SHandler.StudentInfoHandler;
import com.learn.webfluxlearn.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentInfoHandler studentInfoHandler;

    @PostMapping("/create")
    public Mono<Void> createMockStudent(@RequestParam int number) {
        return studentInfoHandler.createMockStudent(number);
    }

    @GetMapping("/random")
    public Mono<Student> getStudentRandomData(){
        return studentInfoHandler.randomStudentQuery();
    }

    @GetMapping("/{id}")
    public Mono<Student> getStudentInfo(@PathVariable String id) {
        return studentInfoHandler.getStudentInfo(id);
    }

    @GetMapping("/age/{start}/{end}")
    public Flux<Student> listStudentByAgeRange(@PathVariable int start, @PathVariable int end) {
        return studentInfoHandler.listStudentByMathScoreRangeUseAll(start, end);
    }

    @GetMapping("/age2/{start}/{end}")
    public Flux<Student> listStudentByAgeRange2(@PathVariable int start, @PathVariable int end) {
        return studentInfoHandler.listStudentByMathScoreRangeUseTemplate(start, end);
    }
}
