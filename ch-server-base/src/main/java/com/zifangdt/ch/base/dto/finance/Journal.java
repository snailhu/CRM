package com.zifangdt.ch.base.dto.finance;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.FinanceBizType;
import com.zifangdt.ch.base.enums.pair.JournalStatus;
import com.zifangdt.ch.base.enums.pair.RevenueOrExpense;
import com.zifangdt.ch.base.enums.pair.TraderType;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/4/2.
 */
@Table(name = "biz_journal")
public class Journal extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -6662720272616394567L;

    @NamedProperty
    private JournalStatus status;
    private Date handleDate;
    @NamedProperty(target = JsonPropertyTarget.OPTION)
    private Long type;
    private String bizNumber;
    @NamedProperty(target = JsonPropertyTarget.OPTION)
    private Long accountId;
    @NamedProperty
    private TraderType traderType;
    private Long traderId;
    private String traderInfo;
    private Long bizId;
    private String bizName;
    @NamedProperty
    private FinanceBizType bizType;
    private List<Long> bizSelfTypes;
    private BigDecimal actualMoney;
    private String createName;
    private Date operateTime;
    private Long operateId;
    private Date invalidTime;
    private Date plannedDate;
    private BigDecimal plannedMoney;
    @NamedProperty
    private RevenueOrExpense revenueOrExpense;
    private JournalDetail[] details;
    @VerboseProperty(target = JsonPropertyTarget.FILE)
    private List<String> attachments;
    private String remark;
    private String operateName;
    private Long handleId;
    private String handleName;
    private Integer contractPeriod;
    private Integer contractCommission;
    private Long projectId;
    private Long invalidId;
    private String invalidName;
    private Long transferId;

    @Transient
    private String accountName;
    @Transient
    private List<String> bizSelfTypeNames;
    @Transient
    private List<Journal> extraDetails;
    @Transient
    private BigDecimal debts;
    @Transient
    private Invoice invoice;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Long getInvalidId() {
        return invalidId;
    }

    public void setInvalidId(Long invalidId) {
        this.invalidId = invalidId;
    }

    public String getInvalidName() {
        return invalidName;
    }

    public void setInvalidName(String invalidName) {
        this.invalidName = invalidName;
    }

    public BigDecimal getDebts() {
        return debts;
    }

    public void setDebts(BigDecimal debts) {
        this.debts = debts;
    }

    public List<Journal> getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(List<Journal> extraDetails) {
        this.extraDetails = extraDetails;
    }

    public List<String> getBizSelfTypeNames() {
        return bizSelfTypeNames;
    }

    public void setBizSelfTypeNames(List<String> bizSelfTypeNames) {
        this.bizSelfTypeNames = bizSelfTypeNames;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getContractCommission() {
        return contractCommission;
    }

    public void setContractCommission(Integer contractCommission) {
        this.contractCommission = contractCommission;
    }

    public Integer getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(Integer contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getAccountName() {
        return accountName;
    }

    public Long getHandleId() {
        return handleId;
    }

    public void setHandleId(Long handleId) {
        this.handleId = handleId;
    }

    public JournalDetail[] getDetails() {
        return details;
    }

    public void setDetails(JournalDetail[] details) {
        this.details = details;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public JournalStatus getStatus() {
        return status;
    }

    public void setStatus(JournalStatus status) {
        this.status = status;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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

    public TraderType getTraderType() {
        return traderType;
    }

    public void setTraderType(TraderType traderType) {
        this.traderType = traderType;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public String getTraderInfo() {
        return traderInfo;
    }

    public void setTraderInfo(String traderInfo) {
        this.traderInfo = traderInfo;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public FinanceBizType getBizType() {
        return bizType;
    }

    public void setBizType(FinanceBizType bizType) {
        this.bizType = bizType;
    }

    public List<Long> getBizSelfTypes() {
        return bizSelfTypes;
    }

    public void setBizSelfTypes(List<Long> bizSelfTypes) {
        this.bizSelfTypes = bizSelfTypes;
    }

    public BigDecimal getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public BigDecimal getPlannedMoney() {
        return plannedMoney;
    }

    public void setPlannedMoney(BigDecimal plannedMoney) {
        this.plannedMoney = plannedMoney;
    }

    public RevenueOrExpense getRevenueOrExpense() {
        return revenueOrExpense;
    }

    public void setRevenueOrExpense(RevenueOrExpense revenueOrExpense) {
        this.revenueOrExpense = revenueOrExpense;
    }


    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class JournalDetail {
        private String name;
        private BigDecimal money;

        public JournalDetail() {
        }

        public JournalDetail(String name, BigDecimal money) {
            this.name = name;
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }
    }
}
