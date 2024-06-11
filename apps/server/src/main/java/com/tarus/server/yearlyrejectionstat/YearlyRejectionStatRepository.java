package com.tarus.server.yearlyrejectionstat;

import com.tarus.server.rejectionreason.RejectionReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface YearlyRejectionStatRepository extends JpaRepository<YearlyRejectionStat,Long> {
   YearlyRejectionStat findByRejectionPercentage(String percentage);
}
