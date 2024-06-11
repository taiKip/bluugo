package com.tarus.server.rejectionreason;

import org.springframework.stereotype.Service;

@Service
public interface RejectionReasonService {
    RejectionReason saveReason(RejectionReason reason);
}
