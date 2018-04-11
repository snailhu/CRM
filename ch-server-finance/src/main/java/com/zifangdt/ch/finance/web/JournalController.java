package com.zifangdt.ch.finance.web;

import com.zifangdt.ch.base.bo.ContractPeriodBo;
import com.zifangdt.ch.base.bo.JournalPurchaseBo;
import com.zifangdt.ch.base.bo.JournalTicketBo;
import com.zifangdt.ch.base.bo.PageResultBo;
import com.zifangdt.ch.base.dto.finance.Invoice;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.enums.pair.FinanceBizType;
import com.zifangdt.ch.base.enums.pair.TraderType;
import com.zifangdt.ch.base.enums.project.RefundNoticeStep;
import com.zifangdt.ch.finance.bo.*;
import com.zifangdt.ch.finance.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/12.
 */
@RestController
@RequestMapping("/journals")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @GetMapping
    public PageResultBo list(@Valid JournalListQuery query) {
        return journalService.list(query);
    }

//    @GetMapping("/export")
//    public void export(@Valid JournalListQuery queryBo, HttpServletResponse response) throws IOException {
//        List<JournalExcelExportBo> list = journalService.findListToExport(queryBo);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, null), JournalExcelExportBo.class, list);
//
//        FileUtil.download(response, workbook, "收支流水.xls");
//    }

    @PostMapping("/done")
    public Long saveDone(@RequestBody @Valid JournalCreateDone bo) {
        return journalService.saveJournal(bo);
    }

    @PostMapping("/pending")
    public Long savePending(@RequestBody @Valid JournalCreatePending bo) {
        return journalService.saveJournal(bo);
    }

    @PostMapping(value = "/contractPeriod", params = "for=internal")
    public void contractPeriod(@RequestBody @Valid ContractPeriodBo bo) {
        journalService.noteContractPeriod(bo);
    }

    @GetMapping(value = "/periodRecords", params = "for=internal")
    public List<Journal> periodRecords(@RequestParam("contractId") Long contractId) {
        return journalService.findContractPeriodRecords(contractId);
    }

    @PostMapping(value = "/ticket", params = "for=internal")
    public void noteTicket(@Valid @RequestBody JournalTicketBo journalTicketBo) {
        journalService.noteTicket(journalTicketBo);
    }

    @PostMapping(value = "/purchase", params = "for=internal")
    public void notePurchase(@Valid @RequestBody JournalPurchaseBo journalPurchaseBo) {
        journalService.notePurchase(journalPurchaseBo);
    }

    @PutMapping(value = "/plannedTime", params = "for=internal")
    public void updatePlannedTimeForInstalment(
            @RequestParam("contractId") Long contractId,
            @RequestParam("contractTypeName") String contractTypeName,
            @RequestParam("procedureName") String procedureName,
            @RequestParam("step") RefundNoticeStep step) {
        journalService.updatePlannedTimeForInstalment(contractId, contractTypeName, procedureName, step);
    }

    @GetMapping("/relatedRecords")
    public List<Journal> relatedRecords(@RequestParam("bizType") FinanceBizType bizType, @RequestParam("bizId") Long bizId) {
        return journalService.relatedRecords(bizType, bizId);
    }

    @GetMapping("/debtsOfOthers")
    public BigDecimal debtsOfOthers(@RequestParam("traderType") TraderType traderType, @RequestParam("traderId") Long traderId) {
        return journalService.sumDebtsOfOthers(traderType, traderId);
    }

    @GetMapping("/debtsOfMyself")
    public BigDecimal debtsOfMyself(@RequestParam("traderType") TraderType traderType, @RequestParam("traderId") Long traderId) {
        return journalService.sumDebtsOfMyself(traderType, traderId);
    }

    @GetMapping("/{id}")
    public Journal getDetail(@PathVariable("id") Long id) {
        return journalService.getDetail(id);
    }

    @PutMapping("/{id}")
    public void updateDone(@PathVariable("id") Long id, @RequestBody @Valid JournalUpdateDone bo) {
        journalService.updateDone(id, bo);
    }

    @PutMapping("/{id}/invalid")
    public void invalid(@PathVariable("id") Long id) {
        journalService.invalid(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        journalService.delete(id);
    }

    @PostMapping("/{id}/invoice")
    public void addInvoice(@PathVariable("id") Long id, @RequestBody @Valid Invoice invoice) {
        journalService.addInvoice(id, invoice);
    }

    @PutMapping("/{id}/invoice")
    public void updateInvoice(@PathVariable("id") Long id, @RequestBody @Valid Invoice invoice) {
        journalService.updateInvoice(id, invoice);
    }

    @PutMapping
    public void batchUpdateDone(@RequestBody @Valid JournalBatchUpdateDone bo){
        journalService.batchUpdateDone(bo);
    }

}
