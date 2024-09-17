package com.learn.webfluxlearn.SHandler;


import com.github.javafaker.Faker;
import com.learn.webfluxlearn.dao.StudentRepository;
import com.learn.webfluxlearn.domain.*;
import com.mongodb.client.model.geojson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.*;

import java.util.Random;

@Component
@Slf4j
public class PoiInfoHandler {



    @Autowired
    private ReactiveMongoTemplate mongoTemplate;



    public Mono<Void> createMockPoi(int number) {
        Faker faker = new Faker();
        Flux<Poi>poiFlux = Flux.range(0, number).map(i -> getFakerPoi(faker));
        return poiFlux
                .buffer(10000)
                .parallel()
                .flatMap(pois -> {
                    log.info("触发插入操作：{}", pois.size());
                    return mongoTemplate.insertAll(pois).then();
                }).then();
    }


    private Poi getFakerPoi(Faker faker) {
        Poi poi = new Poi();
        poi.setName(faker.name().fullName());
        poi.setPoint(new Point(new Position(faker.number().randomDouble(5,-180,180)
                ,faker.number().randomDouble(5,-90,90))));
        return poi;
    }
}
