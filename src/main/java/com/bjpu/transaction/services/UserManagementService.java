package com.bjpu.transaction.services;

import com.bjpu.transaction.domain.dto.user.UserManagementRequest;
import com.bjpu.transaction.exceptions.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.bjpu.transaction.domain.dto.common.TransferConstant.ErrorCode.INVALID_PIN;
import static com.bjpu.transaction.domain.dto.common.TransferConstant.ErrorCode.PREFIX_INT_SOURCE_SYSTEM;

@Service
public class UserManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.user.management.service}")
    private String prefixUserServiceUrl;

    public void validatePin(String pinTransaction, String referenceNumber) {
        String mockUser = "bjpus";
        boolean valid = restTemplate.postForEntity(prefixUserServiceUrl.concat("/user/api/v1/validatepin"),
                UserManagementRequest.builder()
                        .username(mockUser)
                        .pinTransaction(pinTransaction)
                        .build(), boolean.class).getBody();
        if(!valid){
            throw new GenericException(PREFIX_INT_SOURCE_SYSTEM, INVALID_PIN, "INVALID PIN", referenceNumber);
        }
    }
}
