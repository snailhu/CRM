package com.zifangdt.ch.base.bo;

import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2017/10/26.
 */
public class ApprovalUpdateBo {
    private Date endTime;
    private Long status;
    private Long currentOperator;
    private List<Long> approvedOperators;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getCurrentOperator() {
        return currentOperator;
    }

    public void setCurrentOperator(Long currentOperator) {
        this.currentOperator = currentOperator;
    }

    public List<Long> getApprovedOperators() {
        return approvedOperators;
    }

    public void setApprovedOperators(List<Long> approvedOperators) {
        this.approvedOperators = approvedOperators;
    }
}
