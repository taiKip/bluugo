package com.tarus.server.rejectionreason;

import com.tarus.server.carmodel.CarModel;
import com.tarus.server.entitity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class RejectionReason extends BaseEntity {
    @Column(unique = true)
    private String reason;
    @ManyToMany
    private Set<CarModel> carModels;

    public RejectionReason(String reason) {
        this.reason = reason;
    }
}
