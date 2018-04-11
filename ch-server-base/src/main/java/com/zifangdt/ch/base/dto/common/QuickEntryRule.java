package com.zifangdt.ch.base.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.enums.setting.QuickEntryEnum;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 快捷功能入口
 */
@Table(name = "quick_entry_rule")
@JsonIgnoreProperties(value = {"entrys", "organizationList", "excludeOrganizationIds"}, allowGetters = true)
public class QuickEntryRule extends AuditEntity<Long> {
    @NotEmpty
    private String name;

    @NotEmpty
    private List<Long> organizationIds;

    @NotEmpty
    private Long[] quickIds;

    @Length(max = 256)
    private String remark;

    @Transient
    private List<Organization> organizationList;

    @Transient
    private List<Long> excludeOrganizatioIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public List<Long> getOrganizationIds() {
        if (organizationIds == null){
            return new ArrayList<>();
        } else {
            return organizationIds;
        }
    }

    public void setOrganizationIds(List<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public Long[] getQuickIds() {
        if (quickIds == null){
            return new Long[0];
        } else {
            return quickIds;
        }
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @JsonProperty()
    public List<QuickEntryEnum> getEntrys(){
        return Arrays.asList(getQuickIds()).stream().map(QuickEntryEnum::getById).collect(Collectors.toList());
    }

    public List<Organization> getOrganizationList(){
        return organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public List<Long> getExcludeOrganizatioIds() {
        return excludeOrganizatioIds;
    }

    public void setExcludeOrganizatioIds(List<Long> excludeOrganizatioIds) {
        this.excludeOrganizatioIds = excludeOrganizatioIds;
    }

    public void setQuickIds(Long[] quickIds) {
        this.quickIds = quickIds;
    }
}