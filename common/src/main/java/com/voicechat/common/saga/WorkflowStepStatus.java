package com.voicechat.common.saga;

public enum WorkflowStepStatus {
    PENDING("PENDING"),
    FAILED("FAILED"),
    COMPLETED("COMPLETED");

    private final String status;

    WorkflowStepStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
