package com.tarus.server.rejectionreason;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reasons")
public class RejectionReasonController {
    private final RejectionReasonService service;
    @PostMapping
    public ResponseEntity<RejectionReason> saveRejectionReason(@RequestBody RejectionReason reason){
        return  ResponseEntity.ok(service.saveReason(reason));
    }
}
