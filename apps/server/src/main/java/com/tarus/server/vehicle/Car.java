package com.tarus.server.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Vehicle extends BaseEntity {
    private int year;
    private String make;
    private String model;
    private String rejectionPercentage;
    @OneToMany(mappedBy = "vehicle",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Reason> reasons;


}
