package com.tarus.server.yearlyrejectionstat;

import com.tarus.server.carmodel.CarModel;
import com.tarus.server.entitity.BaseEntity;
import com.tarus.server.rejectionreason.RejectionReason;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@Table(name = "yearly_rejection_stats")
public class YearlyRejectionStat extends BaseEntity {
    @Column(name = "rejection_percentage",nullable = false)
    private String rejectionPercentage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id",nullable = false)
    private CarModel carModel;
    @OneToMany(mappedBy = "yearlyRejectionStat",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<RejectionReason> rejectionReasons;
}
