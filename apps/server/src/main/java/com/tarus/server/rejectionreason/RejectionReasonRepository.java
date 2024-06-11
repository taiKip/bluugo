package com.tarus.server.rejectionreason;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RejectionReasonRepository extends JpaRepository<RejectionReason,Long> {
    Optional<RejectionReason> findByRejectionReasonByReasonIgnoreCase(String reason);
}
