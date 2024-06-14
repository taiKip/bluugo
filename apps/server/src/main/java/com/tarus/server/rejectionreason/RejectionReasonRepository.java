package com.tarus.server.rejectionreason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RejectionReasonRepository extends JpaRepository<RejectionReason,Long> {
    Optional<RejectionReason> findByReasonIgnoreCase(String reason);
}
