package com.tarus.server.carmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarModelResponseDto {
    private String modelYear;
    private String make;
    private String model;
    private String rejectionPercentage;
    private List<String> reasons;
}
