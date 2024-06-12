package com.tarus.server.rejectionreason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RejectionReasonRepository extends JpaRepository<RejectionReason,Long> {

 RejectionReason findByDescriptionIgnoreCase(String description);
}
