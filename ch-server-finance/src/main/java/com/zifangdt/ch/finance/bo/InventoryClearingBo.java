package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.dto.ticket.output.TicketBrief;
import com.zifangdt.ch.base.enums.ClearingStatus;
import com.zifangdt.ch.base.enums.pair.ExpenseStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 袁兵 on 2018/2/7.
 */
public class InventoryClearingBo {
    private Long contractType;
    private List<TicketBrief> tickets;
    private ClearingStatus status;
    private BigDecimal sumPlannedMoney;
    private BigDecimal sumActualMoney;
    private String contractTypeName;

    private void calc(List<TicketBrief> tickets) {
        if (!CollectionUtils.isEmpty(tickets)) {
            long countProcessed = 0;
            BigDecimal sumPlannedMoney = BigDecimal.ZERO;
            BigDecimal sumActualMoney = BigDecimal.ZERO;
            for (TicketBrief t : tickets) {
                if (t.getExpenseStatus() == ExpenseStatus.PROCESSED) {
                    countProcessed++;
                }
                sumPlannedMoney = sumPlannedMoney.add(t.getTotal());
                sumActualMoney = sumActualMoney.add(t.getActualTotal());
            }
            this.status = countProcessed == 0 ? ClearingStatus.PENDING : countProcessed == tickets.size() ? ClearingStatus.APPROVED : ClearingStatus.APPROVING;
            this.sumPlannedMoney = sumPlannedMoney;
            this.sumActualMoney = sumActualMoney;
        }
    }

    public InventoryClearingBo() {
    }

    public InventoryClearingBo(Long contractType, List<TicketBrief> tickets) {
        this.contractType = contractType;
        this.tickets = tickets;

        calc(tickets);
    }

    public ClearingStatus getStatus() {
        return status;
    }

    public BigDecimal getSumPlannedMoney() {
        return sumPlannedMoney;
    }

    public BigDecimal getSumActualMoney() {
        return sumActualMoney;
    }

    public Long getContractType() {
        return contractType;
    }

    public void setContractType(Long contractType) {
        this.contractType = contractType;
    }

    public List<TicketBrief> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketBrief> tickets) {
        this.tickets = tickets;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }
}
