package com.bjpu.transaction.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse implements Serializable {
    private String destinationAccountNumber;
    private String destinationAccountName;
    private BigDecimal amount;
    private BigDecimal adminFee;
    private String referenceNumber;
    private String responseCode;
    private String responseDesc;
}
