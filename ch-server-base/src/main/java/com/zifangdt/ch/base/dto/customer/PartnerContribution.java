package com.zifangdt.ch.base.dto.customer;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/25.
 */
@Table(name = "biz_partner_contribution")
public class PartnerContribution implements Serializable {
    private static final long serialVersionUID = -8871334029017070294L;
    @Id
    private Long partnerId;
    @Id
    private Long customerId;
    private Set<Long> contractIds;

    public PartnerContribution() {
    }

    public PartnerContribution(Long partnerId, Long customerId) {
        this.partnerId = partnerId;
        this.customerId = customerId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<Long> getContractIds() {
        return contractIds;
    }

    public void setContractIds(Set<Long> contractIds) {
        this.contractIds = contractIds;
    }
}
