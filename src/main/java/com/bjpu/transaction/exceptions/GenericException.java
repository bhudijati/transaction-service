package com.bjpu.transaction.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericException extends RuntimeException{
    private final String sourceSystem;
    private final String errorCode;
    private final String errorDesc;
    private final String referenceNumber;
}
