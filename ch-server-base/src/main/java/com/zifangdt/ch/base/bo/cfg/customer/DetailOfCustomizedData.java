package com.zifangdt.ch.base.bo.cfg.customer;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public class DetailOfCustomizedData extends DetailOfOption {
    private String phone;
    private String remark;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
