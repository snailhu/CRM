package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.common.Notice;

/**
 * Created by 袁兵 on 2018/1/23.
 */
public class  ProcessOverview {
    private String currentStep;
    private String prevStep;
    private String nextStep;
    private int currentStepIndex;
    private boolean rejected;
    private ProcessInstance process;
    private boolean canApprove;
    private Notice currentNotice;

    public Notice getCurrentNotice() {
        return currentNotice;
    }

    public void setCurrentNotice(Notice currentNotice) {
        this.currentNotice = currentNotice;
    }

    public boolean isCanApprove() {
        return canApprove;
    }

    public void setCanApprove(boolean canApprove) {
        this.canApprove = canApprove;
    }

    public ProcessInstance getProcess() {
        return process;
    }

    public void setProcess(ProcessInstance process) {
        this.process = process;
    }

    public String getPrevStep() {
        return prevStep;
    }

    public void setPrevStep(String prevStep) {
        this.prevStep = prevStep;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    public void setCurrentStepIndex(int currentStepIndex) {
        this.currentStepIndex = currentStepIndex;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
}
