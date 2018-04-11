package com.zifangdt.ch.contract.bo;

import com.zifangdt.ch.base.bo.PageQueryBo;

/**
 * Created by 袁兵 on 2018/1/31.
 */
public class ByCustomerIdQueryBo extends PageQueryBo {
    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
