package com.bjpu.transaction.services;

import com.bjpu.transaction.domain.dto.user.UserManagementRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserManagementServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private UserManagementService userManagementService;

    @Test
    public void testValidatePin_expectValid() {
        when(restTemplate.postForEntity(eq("http://localhost:8083/user/api/v1/validatepin"),
                eq(UserManagementRequest.builder()
                        .username("bjpus")
                        .pinTransaction("123456")
                        .build()), eq(boolean.class)))
                .thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        userManagementService.validatePin("123456", "UID1231231");
        verify(restTemplate, times(1)).postForEntity(eq("http://localhost:8083/user/api/v1/validatepin")
                , eq(UserManagementRequest.builder()
                        .username("bjpus")
                        .pinTransaction("123456")
                        .build()), eq(boolean.class));

    }

    @Test(expected = Exception.class)
    public void testValidatePin_expectException() {
        when(restTemplate.postForEntity(eq("http://localhost:8083/user/api/v1/validatepin"),
                eq(UserManagementRequest.builder()
                        .username("bjpus")
                        .pinTransaction("123456")
                        .build()), eq(boolean.class)))
                .thenReturn(new ResponseEntity<>(false, HttpStatus.OK));
        userManagementService.validatePin("123456", "UID1231231");
    }
}