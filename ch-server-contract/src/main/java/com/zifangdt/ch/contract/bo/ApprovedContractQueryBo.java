package com.zifangdt.ch.contract.bo;

import com.zifangdt.ch.base.bo.PageQueryBo;

/**
 * Created by 袁兵 on 2018/1/31.
 */
public class ApprovedContractQueryBo extends PageQueryBo {
    private String search;
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
