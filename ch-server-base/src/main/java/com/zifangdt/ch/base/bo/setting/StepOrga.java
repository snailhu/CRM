package com.zifangdt.ch.base.bo.setting;

import com.zifangdt.ch.base.validation.OrganizationExist;
import com.zifangdt.ch.base.validation.OrganizationHasBoss;
import com.zifangdt.ch.base.validation.ValidOrder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

/**
 * 步骤，步骤执行人是部门负责人
 */
@GroupSequence({StepOrga.class, ValidOrder.Second.class, ValidOrder.Third.class})
public class StepOrga {

    @NotEmpty
    private String name;

    @NotNull
    @OrganizationExist(groups = ValidOrder.Second.class)
    @OrganizationHasBoss(groups = ValidOrder.Third.class)
    private Long orgaId;

    private Boolean isCharger;

    public Long getOrgaId() {
        return orgaId;
    }

    public void setOrgaId(Long orgaId) {
        this.orgaId = orgaId;
    }

    public Boolean getCharger() {
        return isCharger;
    }

    public void setCharger(Boolean charger) {
        isCharger = charger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
