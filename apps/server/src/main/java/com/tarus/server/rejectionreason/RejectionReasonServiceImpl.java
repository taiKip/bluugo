package com.tarus.server.rejectionreason;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RejectionReasonServiceImpl implements RejectionReasonService {
    @Autowired
    private RejectionReasonRepository rejectionReasonRepository;

    @Override
    public RejectionReason saveReason(RejectionReason reason) {
        RejectionReason reasonDb = rejectionReasonRepository
                .findByDescriptionAndReasonNumberIgnoreCase(
                        reason.getReason(), reason.getReasonNumber());
        if (reasonDb != null) {
            return reasonDb;
        }
        return rejectionReasonRepository.save(reason);

    }
}
