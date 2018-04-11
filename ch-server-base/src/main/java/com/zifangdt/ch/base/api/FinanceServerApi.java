package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.ContractPeriodBo;
import com.zifangdt.ch.base.bo.JournalPurchaseBo;
import com.zifangdt.ch.base.bo.JournalTicketBo;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.enums.project.RefundNoticeStep;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@FeignClient("finance-server")
public interface FinanceServerApi {
    /**
     * 获取财务人员
     *
     * @return
     */
    @GetMapping("/configs/financialStaff")
    Set<Long> financialStaff();

    /**
     * 生成合同分期回款记录
     */
    @PostMapping("/journals/contractPeriod?for=internal")
    void contractPeriod(@RequestBody @Valid ContractPeriodBo bo);

    /**
     * 获取合同的分期回款记录
     */
    @GetMapping("/journals/periodRecords?for=internal")
    List<Journal> periodRecords(@RequestParam("contractId") Long contractId);

    /**
     * 新增一条“工单结算”类型的待支付记录
     * 或者
     * 一条“工单回款”类型的待回款记录
     * 取决于JournalTicketBo#revenueOrExpense
     */
    @PostMapping("/journals/ticket?for=internal")
    void noteTicket(@Valid @RequestBody JournalTicketBo journalTicketBo);

    /**
     * 新增一条“采购支出”类型的待支付记录
     */
    @PostMapping("/journals/purchase?for=internal")
    void notePurchase(@Valid @RequestBody JournalPurchaseBo journalPurchaseBo);

    /**
     * 设置“合同分期回款”类型的待回款分期记录的计划回款时间
     * @param contractId
     * @param contractTypeName
     * @param procedureName
     * @param step
     */
    @PutMapping("/journals/plannedTime?for=internal")
    void updatePlannedTimeForInstalment(
            @RequestParam("contractId") Long contractId,
            @RequestParam("contractTypeName") String contractTypeName,
            @RequestParam("procedureName") String procedureName,
            @RequestParam("step") RefundNoticeStep step);
}
