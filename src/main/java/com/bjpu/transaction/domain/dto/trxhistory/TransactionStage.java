package com.bjpu.transaction.domain.dto.trxhistory;

public enum TransactionStage {
    EXECUTED("EXECUTED");

    String stage;

    TransactionStage(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }
}
