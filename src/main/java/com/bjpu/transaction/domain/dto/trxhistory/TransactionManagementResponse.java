package com.bjpu.transaction.domain.dto.trxhistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionManagementResponse implements Serializable {
    private Long idHistoryTransaction;
    private String sourceAccount;
    private String bankCode;
    private String destAccount;
    private BigDecimal amount;
    private String destinationAccountName;
    private BigDecimal adminFee;
    private String referenceNumber;
    private String transactionStage;
}
