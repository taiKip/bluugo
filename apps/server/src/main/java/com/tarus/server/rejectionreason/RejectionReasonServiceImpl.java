package com.tarus.server.rejectionreason;

import com.tarus.server.yearlyrejectionstat.YearlyRejectionStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RejectionReasonServiceImpl implements RejectionReasonService {
    @Autowired
    private RejectionReasonRepository rejectionReasonRepository;


    @Override
    public RejectionReason createRejectionReason(
            String description,
            YearlyRejectionStat stat) {
        if(description.isEmpty()){
            return null;
        }

        RejectionReason rejectionReason = rejectionReasonRepository.findByDescriptionIgnoreCase(description);
        rejectionReason.setYearlyRejectionStat(stat);
        rejectionReason.setDescription(description.isEmpty() ? null : description);
        return rejectionReason;
    }

    @Override
    public void SaveAllRejectionReasons(List<RejectionReason> reasons) {
        rejectionReasonRepository.saveAll(reasons);
    }
}
