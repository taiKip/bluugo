package com.tarus.server.car;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {
    @JsonProperty("model_year")
    private int modelYear;
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

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getMake() {
        return make;
    }

    public void setMake() {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRejectionPercentage() {
        return rejectionPercentage;
    }

    public void setRejectionPercentage(String rejectionPercentage) {
        this.rejectionPercentage = rejectionPercentage;
    }

    public String getReason1() {
        return reason1;
    }

    public void setReason1(String reason1) {
        this.reason1 = reason1;
    }

    public String getReason2() {
        return reason2;
    }

    public void setReason2(String reason2) {
        this.reason2 = reason2;
    }

    public String getReason3() {
        return reason3;
    }

    public void setReason3(String reason3) {
        this.reason3 = reason3;
    }
}
