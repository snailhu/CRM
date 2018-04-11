package com.zifangdt.ch.base.dto.contract;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.ProcedureNode;
import com.zifangdt.ch.base.validation.ConfiguredOption;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Table(name = "biz_contract_instalment")
public class Instalment extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 5597768824926605302L;
    private Long contractId;
    private Integer serialNumber;
    @NotNull
    @ConfiguredOption(ConfigType.contractTypes)
    @NamedProperty
    private Long contractType;
    @NotNull
    @ConfiguredOption(ConfigType.procedures)
    @NamedProperty
    @Column(name = "`procedure`")
    private Long procedure;
    @NotNull
    @NamedProperty
    private ProcedureNode node;
    @NotNull
    private BigDecimal plannedMoney;

    public ProcedureNode getNode() {
        return node;
    }

    public void setNode(ProcedureNode node) {
        this.node = node;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getContractType() {
        return contractType;
    }

    public void setContractType(Long contractType) {
        this.contractType = contractType;
    }

    public Long getProcedure() {
        return procedure;
    }

    public void setProcedure(Long procedure) {
        this.procedure = procedure;
    }

    public BigDecimal getPlannedMoney() {
        return plannedMoney;
    }

    public void setPlannedMoney(BigDecimal plannedMoney) {
        this.plannedMoney = plannedMoney;
    }
}
