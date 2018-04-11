package com.zifangdt.ch.finance.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.finance.bo.JournalBatchUpdateDone;
import com.zifangdt.ch.finance.bo.JournalListQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/9.
 */
@Repository
public interface JournalMapper extends BaseMapper<Journal> {
    List<Journal> getList(JournalListQuery query);

    List<Journal> getListForSumMoney(JournalListQuery query);

    int maxContractPeriod(@Param("contractId") Long contractId, @Param("typeOfContractPeriod") Long typeOfContractPeriod);

    int maxContractCommission(@Param("contractId") Long contractId, @Param("typeOfContractCommission") Long typeOfContractCommission);

    List<Journal> findContractPeriodRecords(@Param("contractId") Long contractId, @Param("typeOfContractPeriod") Long typeOfContractPeriod);

    void updatePlannedTimeForInstalment(@Param("contractId") Long contractId, @Param("typeOfContractPeriod") Long typeOfContractPeriod, @Param("name") String name);

    List<Journal> listForInventory(@Param("projectId") Long projectId);

    List<Journal> listForInventoryByType(@Param("projectId") Long projectId, @Param("type") Long type);

    List<Journal> relatedRecords(@Param("bizType") int bizType, @Param("bizId") Long bizId);

    BigDecimal sumDebts(@Param("status") int status, @Param("traderType") int traderType, @Param("traderId") Long traderId);

    Journal getDetail(Long id);

    List<Journal> findContractCommissionRecords(@Param("contractId") Long contractId, @Param("typeOfContractCommission") Long typeOfContractCommission);

    Long getInvoiceId(Long id);

    List<Journal> getByIdsForBulkUpdate(List<Long> ids);

    void batchUpdateDone(JournalBatchUpdateDone bo);

    boolean existsAdjustRecord(@Param("accountId") Long accountId, @Param("typeOfAdjust") Long typeOfAdjust);
}
