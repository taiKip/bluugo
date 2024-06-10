package com.tarus.server.Util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tarus.server.reason.Reason;
import com.tarus.server.car.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CarDeserializer extends JsonDeserializer<Car> {
    private static final Logger log = LoggerFactory.getLogger(CarDeserializer.class);

    @Override
    public Car deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node  = jsonParser.getCodec().readTree(jsonParser);

        Car car = new Car();
        car.setModelYear(Integer.parseInt(node.get("model_year").asText()));
        car.setMake(node.get("make").asText());
        car.setModel(node.get("model").asText());
        car.setRejectionPercentage(node.get("rejection_percentage").asText());

        Set<Reason> reasons = new HashSet<>();
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()){
            String fieldName = fieldNames.next();
            if(fieldName.toLowerCase().startsWith("reason")){
                String reasonDesc = node.get(fieldName).asText();
                if(!reasonDesc.isEmpty()&& reasonDesc!=null){
                    Reason reason = new Reason();
                    reason.setReason(reasonDesc);
                    reason.setCar(car);
log.info(String.valueOf(reason));
                }
            }

        }
        car.setReasons(reasons);
        return car;
    }
}
