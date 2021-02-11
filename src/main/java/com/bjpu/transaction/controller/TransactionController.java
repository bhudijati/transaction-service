package com.bjpu.transaction.controller;

import com.bjpu.transaction.domain.dto.ResultCode;
import com.bjpu.transaction.domain.dto.TransactionRequest;
import com.bjpu.transaction.domain.dto.TransactionResponse;
import com.bjpu.transaction.domain.dto.audit.AuditActivity;
import com.bjpu.transaction.domain.dto.audit.AuditRequest;
import com.bjpu.transaction.domain.dto.common.TransferConstant;
import com.bjpu.transaction.domain.dto.trxhistory.TransactionManagementRequest;
import com.bjpu.transaction.domain.dto.trxhistory.TransactionManagementResponse;
import com.bjpu.transaction.exceptions.GenericException;
import com.bjpu.transaction.services.AuditManagementService;
import com.bjpu.transaction.services.TransactionManagementService;
import com.bjpu.transaction.services.TransferOtherBankService;
import com.bjpu.transaction.services.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/v1")
@Slf4j
public class TransactionController {

    @Autowired
    private AuditManagementService auditManagementService;

    @Autowired
    private TransferOtherBankService transferOtherBankService;

    @Autowired
    private TransactionManagementService transactionManagementService;

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping(value = "/validatetransaction")
    @ResponseBody
    public TransactionResponse validateTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse;
        TransactionManagementResponse transactionManagementResponse = null;
        ResultCode resultCode = ResultCode.builder()
                .resultAndCode(TransferConstant.APPROVED_CODE)
                .build();
        TransactionManagementRequest transactionManagementRequest = new TransactionManagementRequest();
        try {
            userManagementService.validatePin(transactionRequest.getTransactionPin(), transactionRequest.getTransactionId());
            transactionManagementResponse = transactionManagementService.getTransactionHistory(transactionRequest.getTransactionId());
            transactionResponse = transferOtherBankService.postingPayment(transactionManagementResponse, transactionManagementRequest);
        } catch (Exception ex) {
            log.error("Exception happened when doing inquiry message [{}]", ex.getMessage());
            throw throwHandler(ex, resultCode);
        } finally {
            TransactionManagementResponse finalTransactionManagementResponse = transactionManagementResponse;
            CompletableFuture<Void> audit = CompletableFuture.runAsync(() -> auditManagementService.sendAudit(AuditRequest.builder()
                    .activityName(AuditActivity.EXECUTE)
                    .sourceAccount(finalTransactionManagementResponse != null ? finalTransactionManagementResponse.getSourceAccount() : "")
                    .destinationAccount(finalTransactionManagementResponse != null ? finalTransactionManagementResponse.getDestAccount() : "")
                    .resultCode(resultCode.getResultAndCode())
                    .referenceNumber(transactionRequest.getTransactionId())
                    .build()));
            CompletableFuture<Void> history = CompletableFuture.runAsync(() ->
                    transactionManagementService.sendTransactionalData(transactionManagementRequest));
            CompletableFuture.allOf(audit, history).join();
        }
        return transactionResponse;
    }

    private GenericException throwHandler(Exception ex, ResultCode resultCode) {
        String sourceSystem = "";
        String errorCode = "";
        String errorDesc = "";
        String referenceNumber = "";
        if (ex instanceof GenericException) {
            sourceSystem = ((GenericException) ex).getSourceSystem();
            errorCode = ((GenericException) ex).getErrorCode();
            errorDesc = ((GenericException) ex).getErrorDesc();
            referenceNumber = ((GenericException) ex).getReferenceNumber();

        }
        resultCode.setResultAndCode(sourceSystem.concat(":").concat(errorCode));
        return new GenericException(sourceSystem, errorCode, errorDesc, referenceNumber);
    }
}
