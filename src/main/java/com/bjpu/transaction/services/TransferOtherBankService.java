package com.bjpu.transaction.services;

import com.bjpu.transaction.domain.dto.TransactionResponse;
import com.bjpu.transaction.domain.dto.trxhistory.TransactionManagementRequest;
import com.bjpu.transaction.domain.dto.trxhistory.TransactionManagementResponse;
import com.bjpu.transaction.domain.dto.trxhistory.TransactionStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static com.bjpu.transaction.domain.dto.common.TransferConstant.APPROVED_CODE;

@Service
public class TransferOtherBankService {

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse postingPayment(TransactionManagementResponse transactionManagementResponse,
                                              TransactionManagementRequest transactionManagementRequest) {
        transactionManagementRequest.setIdHistoryTransaction(transactionManagementResponse.getIdHistoryTransaction());
        transactionManagementRequest.setTransactionStage(TransactionStage.EXECUTED.getStage());
        transactionManagementRequest.setReferenceNumber(transactionManagementResponse.getReferenceNumber());
        return TransactionResponse.builder()
                .adminFee(BigDecimal.valueOf(6500))
                .amount(transactionManagementResponse.getAmount())
                .destinationAccountName("Mocking Desti")
                .destinationAccountNumber(transactionManagementResponse.getDestAccount())
                .referenceNumber(transactionManagementResponse.getReferenceNumber())
                .responseCode(APPROVED_CODE)
                .responseDesc("APPROVED")
                .build();
    }
}
