package com.zifangdt.ch.base.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.zifangdt.ch.base.validation.MobileNumber;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 袁兵 on 2017/9/7.
 */
@ExcelTarget("User")
public class UserExcelBo {
    @NotBlank(message = "姓名不能为空")
    @Excel(name = "姓名*")
    private String name;
    @NotBlank(message = "登录名不能为空")
    @Excel(name = "登录名*")
    private String username;
    @NotBlank(message = "职级不能为空")
    @Excel(name = "职级*")
    private String levelName;
    @NotBlank(message = "手机号不能为空")
    @MobileNumber
    @Excel(name = "手机号*",width = 14)
    private String phone;
    @Excel(name = "部门*")
    private String organizationName;
    @NotBlank(message = "登录限制不能为空")
    @Excel(name = "登录限制*")
    private String loginLimitName;

    @Excel(name = "失败原因",width = 100)
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getLoginLimitName() {
        return loginLimitName;
    }

    public void setLoginLimitName(String loginLimitName) {
        this.loginLimitName = loginLimitName;
    }
}
