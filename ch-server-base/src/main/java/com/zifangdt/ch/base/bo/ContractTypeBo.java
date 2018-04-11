package com.zifangdt.ch.base.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 合同类型
 */
public class ContractTypeBo {
    @NotNull
    private Long typeId;
    @NotBlank(message = "类型名称不能为空")
    private String typeName;

    //工序
    private List<ContractTypeUnitBo> typeUnitBos;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<ContractTypeUnitBo> getTypeUnitBos() {
        return typeUnitBos;
    }

    public void setTypeUnitBos(List<ContractTypeUnitBo> typeUnitBos) {
        this.typeUnitBos = typeUnitBos;
    }
}