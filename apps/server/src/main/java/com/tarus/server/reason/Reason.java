package com.tarus.server.reason;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tarus.server.entitity.BaseEntity;
import com.tarus.server.car.Car;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reason extends BaseEntity {
    private String reason;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    @JsonBackReference
    private Car car;


}
