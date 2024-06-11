package com.tarus.server.rejectionreason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RejectionReasonRepository extends JpaRepository<RejectionReason,Long> {

    @Query("SELECT r FROM Reason r WHERE LOWER(r.description) = LOWER(:description) AND r.id = :reasonNumber")
    RejectionReason findByDescriptionAndReasonNumberIgnoreCase(@Param("description") String description,
                                                               @Param("reasonNumber")int reasonNumber);
}
