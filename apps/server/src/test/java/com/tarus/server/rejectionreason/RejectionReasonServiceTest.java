package com.tarus.server.rejectionreason;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RejectionReasonServiceTest {
    @Autowired
private RejectionReasonService service;
    @MockBean
    private RejectionReasonRepository repository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void saveRejectionReason() {
    }
}