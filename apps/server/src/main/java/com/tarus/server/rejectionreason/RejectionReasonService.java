package com.tarus.server.rejectionreason;

import com.tarus.server.yearlyrejectionstat.YearlyRejectionStat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RejectionReasonService {

   public RejectionReason createRejectionReason(String description,YearlyRejectionStat stat);
   public void SaveAllRejectionReasons(List<RejectionReason> reasons);
}
