package com.tarus.server.carmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarModelDto{
    @JsonProperty("model_year")
    private String modelYear;
    private String make;
    private String model;
    @JsonProperty("rejection_percentage")
    private String rejectionPercentage;
    @JsonProperty("reason_1")
    private String reason1;
    @JsonProperty("reason_2")
    private String reason2;
    @JsonProperty("reason_3")
    private String reason3;
}
