package com.learn.webfluxlearn.domain;

import com.mongodb.client.model.geojson.Point;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Document("poi")
@Data
public class Poi{

    @Id
    private String id;

    private String name;

    @Field("point")
    private Point point;

}
