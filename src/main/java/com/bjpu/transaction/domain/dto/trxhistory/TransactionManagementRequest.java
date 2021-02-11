package com.bjpu.transaction.domain.dto.trxhistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionManagementRequest implements Serializable {
    private Long idHistoryTransaction;
    private String referenceNumber;
    private String transactionStage;
}
