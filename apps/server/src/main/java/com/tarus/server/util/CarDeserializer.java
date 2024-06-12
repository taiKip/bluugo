package com.tarus.server.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tarus.server.car.Car;

import java.io.IOException;

public class CarDeserializer extends JsonDeserializer<Car> {
    @Override
    public Car deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Car car = new Car();
        car.setMake(node.get("make").asText());
        car.setModel(node.get("model").asText());
        car.setRejectionPercentage(node.get("rejection_percentage").asText());
        car.setModelYear(Integer.parseInt(node.get("model_year").asText()));
        car.setReason1(node.get("reason_1").asText());
        car.setReason2(node.get("reason_2").asText());
        car.setReason3(node.get("reason_3").asText());

        return car;
    }
}
