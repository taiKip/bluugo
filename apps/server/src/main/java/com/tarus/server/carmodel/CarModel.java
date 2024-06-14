package com.tarus.server.carmodel;

import com.tarus.server.entitity.BaseEntity;
import com.tarus.server.make.Make;
import com.tarus.server.model.Model;
import com.tarus.server.rejectionreason.RejectionReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarModel extends BaseEntity {
    private String modelYear;
    private String rejectionPercentage;
    @ManyToOne
    @JoinColumn(name = "car_make_id")
    private Make make;
    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private Model model;
    @ManyToMany
    @JoinTable(
            name = "car_model_rejection_reason",
            joinColumns = @JoinColumn(name = "car_model_id"),
            inverseJoinColumns = @JoinColumn(name = "rejection_reason_id")
    )
    private Set<RejectionReason> rejectionReasons;
}
