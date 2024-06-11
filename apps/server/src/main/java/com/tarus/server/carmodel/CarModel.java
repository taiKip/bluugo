package com.tarus.server.carmodel;

import com.tarus.server.entitity.BaseEntity;
import com.tarus.server.yearlyrejectionstat.YearlyRejectionStat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_model")
public class CarModel extends BaseEntity {
    private int modelYear;
    private String make;
    private String model;
    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YearlyRejectionStat> yearlyRejectionStats;
}
