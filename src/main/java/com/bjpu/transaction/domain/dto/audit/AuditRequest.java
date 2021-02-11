package com.bjpu.transaction.domain.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditRequest implements Serializable {
    private AuditActivity activityName;
    private String sourceAccount;
    private String destinationAccount;
    private String resultCode;
    private String referenceNumber;
}
