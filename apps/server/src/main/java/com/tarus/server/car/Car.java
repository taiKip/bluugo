package com.tarus.server.car;

import com.tarus.server.entitity.BaseEntity;
import com.tarus.server.reason.Reason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car extends BaseEntity {
    private int modelYear;
    private String make;
    private String model;
    private String rejectionPercentage;
    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Reason> reasons;


}
