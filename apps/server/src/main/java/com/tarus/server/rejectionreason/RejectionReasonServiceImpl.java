package com.tarus.server.rejectionreason;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RejectionReasonServiceImpl implements RejectionReasonService{
    private final RejectionReasonRepository repository;
    @Override
    public RejectionReason saveRejectionReason(String reason) {
        return repository.findByReasonIgnoreCase(reason)
                .orElseGet(()->repository.save(new RejectionReason(reason)));
    }
}
