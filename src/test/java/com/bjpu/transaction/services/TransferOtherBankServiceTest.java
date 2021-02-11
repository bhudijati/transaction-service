package com.bjpu.transaction.services;

import com.bjpu.transaction.domain.dto.TransactionResponse;
import com.bjpu.transaction.domain.dto.trxhistory.TransactionManagementRequest;
import com.bjpu.transaction.domain.dto.trxhistory.TransactionManagementResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransferOtherBankServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TransferOtherBankService transferOtherBankService;

    @Test
    public void testPostingPayment_expectSuccess() {
        TransactionResponse transactionResponse = transferOtherBankService.postingPayment(TransactionManagementResponse.builder()
                        .idHistoryTransaction(1L)
                        .referenceNumber("UDI1231231")
                        .amount(BigDecimal.valueOf(6000))
                        .destAccount("010010101")
                        .build(),
                TransactionManagementRequest.builder().build());
        assertEquals("00", transactionResponse.getResponseCode());
    }
}