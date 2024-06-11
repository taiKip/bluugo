package com.tarus.server.rejectionreason;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RejectionReasonServiceImpl implements RejectionReasonService {
    @Autowired
    private RejectionReasonRepository rejectionReasonRepository;
    @Override
    public RejectionReason saveReason(RejectionReason reason) {
        Optional<RejectionReason> reasonDb = rejectionReasonRepository
                .findByRejectionReasonByReasonIgnoreCase(reason.getReason());
        if(!reasonDb.isEmpty()){
            return reasonDb.get();
        }
        return rejectionReasonRepository.save(reason);

    }
}
