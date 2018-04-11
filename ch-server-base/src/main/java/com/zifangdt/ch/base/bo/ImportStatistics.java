package com.zifangdt.ch.base.bo;

/**
 * Created by 袁兵 on 2017/9/28.
 */
public class ImportStatistics {
    private int successCount;
    private int failureCount;
    private String failureExcel;

    public ImportStatistics(int successCount, int failureCount) {
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public String getFailureExcel() {
        return failureExcel;
    }

    public void setFailureExcel(String failureExcel) {
        this.failureExcel = failureExcel;
    }
}
