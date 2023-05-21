package com.voicechat.common.saga;

public abstract class AbstractWorkflowStep implements WorkflowStep {
    private WorkflowStepStatus workflowStepStatus
            = WorkflowStepStatus.PENDING;

    @Override
    public WorkflowStepStatus getStatus() {
        return this.workflowStepStatus;
    }

    protected void setWorkflowStepStatus(WorkflowStepStatus workflowStepStatus) {
        this.workflowStepStatus = workflowStepStatus;
    }
}
