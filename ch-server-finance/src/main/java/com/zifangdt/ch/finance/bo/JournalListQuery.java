package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.bo.PageQueryBo;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.FinanceBizType;
import com.zifangdt.ch.base.enums.pair.JournalStatus;
import com.zifangdt.ch.base.enums.pair.RevenueOrExpense;
import com.zifangdt.ch.base.enums.pair.TraderType;
import com.zifangdt.ch.base.validation.ConfiguredOption;

import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public class JournalListQuery extends PageQueryBo {
    private String bizNumber;
    private Long accountId;
    private FinanceBizType bizType;
    private Long bizSelfType;
    private JournalStatus status;
    private TraderType traderType;
    private List<String> traderInfo;
    private String operateName;
    private RevenueOrExpense revenueOrExpense;
    private Date handleDateStart;
    private Date handleDateEnd;
    private Date createDateStart;
    private Date createDateEnd;
    private Date operateDateStart;
    private Date operateDateEnd;
    @ConfiguredOption({ConfigType.journalTypes})
    private Long type;
    private String handleName;

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getBizNumber() {
        return bizNumber;
    }

    public void setBizNumber(String bizNumber) {
        this.bizNumber = bizNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public FinanceBizType getBizType() {
        return bizType;
    }

    public void setBizType(FinanceBizType bizType) {
        this.bizType = bizType;
    }

    public Long getBizSelfType() {
        return bizSelfType;
    }

    public void setBizSelfType(Long bizSelfType) {
        this.bizSelfType = bizSelfType;
    }

    public JournalStatus getStatus() {
        return status;
    }

    public void setStatus(JournalStatus status) {
        this.status = status;
    }

    public TraderType getTraderType() {
        return traderType;
    }

    public void setTraderType(TraderType traderType) {
        this.traderType = traderType;
    }

    public List<String> getTraderInfo() {
        return traderInfo;
    }

    public void setTraderInfo(List<String> traderInfo) {
        this.traderInfo = traderInfo;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public RevenueOrExpense getRevenueOrExpense() {
        return revenueOrExpense;
    }

    public void setRevenueOrExpense(RevenueOrExpense revenueOrExpense) {
        this.revenueOrExpense = revenueOrExpense;
    }

    public Date getHandleDateStart() {
        return handleDateStart;
    }

    public void setHandleDateStart(Date handleDateStart) {
        this.handleDateStart = handleDateStart;
    }

    public Date getHandleDateEnd() {
        return handleDateEnd;
    }

    public void setHandleDateEnd(Date handleDateEnd) {
        this.handleDateEnd = handleDateEnd;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getOperateDateStart() {
        return operateDateStart;
    }

    public void setOperateDateStart(Date operateDateStart) {
        this.operateDateStart = operateDateStart;
    }

    public Date getOperateDateEnd() {
        return operateDateEnd;
    }

    public void setOperateDateEnd(Date operateDateEnd) {
        this.operateDateEnd = operateDateEnd;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
