package com.tarus.server.rejectionreason;

import com.tarus.server.entitity.BaseEntity;
import com.tarus.server.yearlyrejectionstat.YearlyRejectionStat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Table(name = "rejection_reason")
@NoArgsConstructor
public class RejectionReason extends BaseEntity {
    @Column(unique = true)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yearly_rejection_stat_id")
    private YearlyRejectionStat yearlyRejectionStat;
}
