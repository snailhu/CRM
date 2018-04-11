package com.zifangdt.ch.finance.bo;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * Created by 袁兵 on 2018/3/15.
 */
@ExcelTarget("Journal")
public class JournalExcelExportBo {
    @Excel(name = "ID")
    private Long id;
    @Excel(name = "收支时间",width = 20)
    private String time;

    @Excel(name = "收支类型",width = 20)
    private String typeName;
    @Excel(name = "收支")
    private String revenueOrExpense;
    @Excel(name = "结算账户")
    private String accountName;

    @Excel(name = "金额")
    private String money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRevenueOrExpense() {
        return revenueOrExpense;
    }

    public void setRevenueOrExpense(String revenueOrExpense) {
        this.revenueOrExpense = revenueOrExpense;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

