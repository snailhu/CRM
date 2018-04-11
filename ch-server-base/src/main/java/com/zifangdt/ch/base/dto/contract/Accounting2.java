package com.zifangdt.ch.base.dto.contract;

import com.zifangdt.ch.base.validation.PositiveNumber;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Table(name = "biz_contract_accounting2")
public class Accounting2 implements Serializable {
    private static final long serialVersionUID = -1940520212357902716L;

    @Id
    private Long contractId;
    @NotNull
    @PositiveNumber
    private Float royaltyRate;
    @NotNull
    @PositiveNumber
    private BigDecimal projectCommission;
    private String remark;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Float getRoyaltyRate() {
        return royaltyRate;
    }

    public void setRoyaltyRate(Float royaltyRate) {
        this.royaltyRate = royaltyRate;
    }

    public BigDecimal getProjectCommission() {
        return projectCommission;
    }

    public void setProjectCommission(BigDecimal projectCommission) {
        this.projectCommission = projectCommission;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
