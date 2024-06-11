package com.tarus.server.rejectionreason;

import com.tarus.server.entitity.BaseEntity;
import com.tarus.server.yearlyrejectionstat.YearlyRejectionStat;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rejection_reason")
public class RejectionReason extends BaseEntity {
    private String reason;
    private  int reasonNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yearly_rejection_stat_id")
    private YearlyRejectionStat yearlyRejectionStat;
}
