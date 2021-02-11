package com.bjpu.transaction.domain.dto.common;

public class TransferConstant {
    private TransferConstant() {
        //hide for implicit sonar
    }

    public static final String APPROVED_CODE = "00";

    public static class ErrorCode {
        private ErrorCode() {
            //hide for implicit sonar
        }

        public static final String PREFIX_INT_SOURCE_SYSTEM = "INT";
        public static final String INVALID_PIN = "0015";
    }
}
