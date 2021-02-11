package com.bjpu.transaction.services;

import com.bjpu.transaction.domain.dto.audit.AuditRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuditManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.audit.management.service}")
    private String prefixAuditServiceUrl;

    public void sendAudit(AuditRequest auditRequest){
        restTemplate.postForEntity(prefixAuditServiceUrl.concat("/audit/api/v1/store"),
                auditRequest, HttpStatus.class);
    }
}
