package com.tarus.server.dto;

import java.util.List;

public record CarReasonDto(String model_year,
                           String make,
                           String model,
                           String rejection_percentage, List<String> reasons) {
}
