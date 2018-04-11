package com.zifangdt.ch.uaa.bo;

import com.zifangdt.ch.base.enums.pair.LoginLimit;
import com.zifangdt.ch.base.validation.IntegerEnumValue;
import com.zifangdt.ch.base.validation.MobileNumber;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public class UserUpdateBo {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @MobileNumber
    @NotBlank
    private String phone;
    private Long organizationId;
    private String tag;
    private String description;
    @IntegerEnumValue({0,1})
    private Integer gender;
    private Date birthday;

    private String avatar;

    private LoginLimit loginLimit;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LoginLimit getLoginLimit() {
        return loginLimit;
    }

    public void setLoginLimit(LoginLimit loginLimit) {
        this.loginLimit = loginLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
