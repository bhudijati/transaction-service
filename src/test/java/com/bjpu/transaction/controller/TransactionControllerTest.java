package com.bjpu.transaction.controller;

import com.bjpu.transaction.domain.dto.TransactionRequest;
import com.bjpu.transaction.exceptions.GenericException;
import com.bjpu.transaction.services.AuditManagementService;
import com.bjpu.transaction.services.TransactionManagementService;
import com.bjpu.transaction.services.TransferOtherBankService;
import com.bjpu.transaction.services.UserManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuditManagementService auditManagementService;

    @MockBean
    private TransferOtherBankService transferOtherBankService;

    @MockBean
    private TransactionManagementService transactionManagementService;

    @MockBean
    private UserManagementService userManagementService;

    @SneakyThrows
    @Test
    public void validateTransaction_expectSuccess() {
        mockMvc.perform(post("/v1/validatetransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionRequest.builder()
                        .transactionId("UID876534")
                        .transactionPin("123456")
                        .build())))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void validateTransaction_expectException() {
        doThrow(GenericException.class).when(userManagementService).validatePin(any(), any());
        mockMvc.perform(post("/v1/validatetransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionRequest.builder()
                        .transactionId("UID876534")
                        .transactionPin("123456")
                        .build())))
                .andExpect(status().isBadRequest());
    }
}