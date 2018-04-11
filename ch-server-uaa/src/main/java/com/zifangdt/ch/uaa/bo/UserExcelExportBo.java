package com.zifangdt.ch.uaa.bo;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * Created by 袁兵 on 2017/11/8.
 */
@ExcelTarget("User")
public class UserExcelExportBo {
    @Excel(name = "ID")
    private Long id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "登录名")
    private String username;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "部门")
    private String organizationName;
    @Excel(name = "职级")
    private String levelName;
    @Excel(name = "状态")
    private String statusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
