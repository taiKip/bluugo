package com.tarus.server.dto;

import java.util.List;

public record JsonDto(String model_year,
                      String make,
                      String model,
                      String rejection_percentage,
                      String reason_1,String reason_2,String reason_3) {

}
