package com.zifangdt.ch.base.dto.customer.setting;

import com.zifangdt.ch.base.dto.AuditEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "cfg_customer_source_item")
public class CfgCustomerSourceItem extends AuditEntity<Long> {

    private Long customerSourceId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    private String remark;

    public Long getCustomerSourceId() {
        return customerSourceId;
    }

    public void setCustomerSourceId(Long customerSourceId) {
        this.customerSourceId = customerSourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}