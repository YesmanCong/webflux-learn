package com.learn.webfluxlearn.web;


import com.learn.webfluxlearn.SHandler.*;
import com.learn.webfluxlearn.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
@RequestMapping("/poi")
public class PoiController {
    @Autowired
    private PoiInfoHandler poiInfoHandler ;

    @PostMapping("/create")
    public Mono<Void> createMockStudent(@RequestParam int number) {
        return poiInfoHandler.createMockPoi(number);
    }


}
