package com.bjpu.transaction.domain.dto.audit;

public enum AuditActivity {
    EXECUTE("Executing for Transfer to Other Bank");

    String activity;

    AuditActivity(String activity){
        this.activity = activity;
    }
    public String getActivity() {
        return activity;
    }
}
