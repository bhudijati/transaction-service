package com.bjpu.transaction.services;

import com.bjpu.transaction.domain.dto.audit.AuditRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AuditManagementServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private AuditManagementService auditManagementService;

    @Test
    public void testSendAudit_expectCalledRestTemplate() {
        auditManagementService.sendAudit(AuditRequest.builder().build());
        verify(restTemplate, times(1)).postForEntity(anyString(), any(AuditRequest.class), eq(HttpStatus.class));
    }


}