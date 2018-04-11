package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.validation.ConfiguredOption;
import com.zifangdt.ch.base.validation.MobileNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public class CustomerCreateBo {
    @NotBlank
    private String name;
    @NotBlank
    @MobileNumber
    private String phone;
    @NotBlank
    private String address;
    @NotNull
    @ConfiguredOption(ConfigType.customerSources)
    private Long source;
    private Long sourceRelationId;
    private String remark;

    private String tag;
    @Email
    private String email;
    @Pattern(regexp = "^(\\d{3,4}-)?\\d{7,8}$", message = "传真格式不正确")
    private String fax;
    private String secondaryContactName;
    private String secondaryContactPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getSourceRelationId() {
        return sourceRelationId;
    }

    public void setSourceRelationId(Long sourceRelationId) {
        this.sourceRelationId = sourceRelationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSecondaryContactName() {
        return secondaryContactName;
    }

    public void setSecondaryContactName(String secondaryContactName) {
        this.secondaryContactName = secondaryContactName;
    }

    public String getSecondaryContactPhone() {
        return secondaryContactPhone;
    }

    public void setSecondaryContactPhone(String secondaryContactPhone) {
        this.secondaryContactPhone = secondaryContactPhone;
    }
}
