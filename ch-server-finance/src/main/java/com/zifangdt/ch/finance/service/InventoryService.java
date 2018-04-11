package com.zifangdt.ch.finance.service;

import com.zifangdt.ch.base.api.ContractServerApi;
import com.zifangdt.ch.base.api.ManagerServerApi;
import com.zifangdt.ch.base.api.ProductServerApi;
import com.zifangdt.ch.base.bo.InventoryBo;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.finance.InventoryItem;
import com.zifangdt.ch.base.dto.finance.InventoryRecord;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.dto.product.output.ProjectOutStockProduct;
import com.zifangdt.ch.base.enums.pair.JournalStatus;
import com.zifangdt.ch.base.enums.pair.RevenueOrExpense;
import com.zifangdt.ch.base.enums.pair.TaskStatus;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.finance.bo.InventoryClearingBo;
import com.zifangdt.ch.finance.bo.InventoryCostBo;
import com.zifangdt.ch.finance.mapper.InventoryRecordMapper;
import com.zifangdt.ch.finance.mapper.JournalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/30.
 */
@Service
public class InventoryService {

    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private JournalService journalService;
    @Autowired
    private ContractServerApi contractServerApi;
    @Autowired
    private ProductServerApi productServerApi;
    @Autowired
    private InventoryRecordMapper inventoryRecordMapper;
    @Autowired
    private ManagerServerApi managerServerApi;

    public List<InventoryClearingBo> clearingBos(Long projectId) {
        return journalService.clearingBos(projectId);
    }

    public Map<String, Object> details(Long projectId) {
        int status = managerServerApi.getProjectInventoryStatus(projectId);
        if (status != TaskStatus.InventoryStatus.INVENTORY.getIntVerifier() && status != TaskStatus.InventoryStatus.HAVE_INVENTORY.getIntVerifier()) {
            throw new WrongOperationException("该项目不是盘点中或已盘点状态");
        }

        Map<String, Object> result = journalService.journalsForInventory(projectId);
        //成本
        Contract contract = contractServerApi.getDetailByProjectId(projectId);
        InventoryCostBo costBo = new InventoryCostBo();
        costBo.setDesignCost(contract.getAccounting1().getDesignCost());
        costBo.setExtendServiceCost(contract.getAccounting1().getExtendServiceCost());
        costBo.setGiftCost(contract.getAccounting1().getHasGift() ? contract.getAccounting1().getGiftCost() : BigDecimal.ZERO);
        costBo.setTotalCost(contract.getAccounting1().getTotalCost());
        costBo.setMaterialCosts(productServerApi.getForProjectOut(projectId));
        result.put("cost", costBo);

        return result;
    }

    public void startInventory(Long projectId) {
        int status = managerServerApi.getProjectInventoryStatus(projectId);
        if (status != TaskStatus.InventoryStatus.WAIT_INVENTORY.getIntVerifier()) {
            throw new WrongOperationException("该项目不是待盘点状态");
        }
        InventoryBo bo = new InventoryBo();
        bo.setInventoryStatus(TaskStatus.InventoryStatus.INVENTORY);
        bo.setProjectId(projectId);
        managerServerApi.changeInventoryStatus(bo);
    }

    public void doInventory(Long projectId) {
        int status = managerServerApi.getProjectInventoryStatus(projectId);
        if (status != TaskStatus.InventoryStatus.INVENTORY.getIntVerifier()) {
            throw new WrongOperationException("该项目不是盘点中状态");
        }

        InventoryRecord record = generateRecord(projectId);
        inventoryRecordMapper.insertSelective(record);

        InventoryBo bo = new InventoryBo();
        bo.setProjectId(projectId);
        bo.setInventoryStatus(TaskStatus.InventoryStatus.HAVE_INVENTORY);
        bo.setPayOut(Arrays.stream(record.getExpenseDetail()).reduce(BigDecimal.ZERO, (sum, n) -> sum.add(n.getMoney()), BigDecimal::add).doubleValue());
        bo.setIncome(Arrays.stream(record.getRevenueDetail()).reduce(BigDecimal.ZERO, (sum, n) -> sum.add(n.getMoney()), BigDecimal::add).doubleValue());
        bo.setSelfCost(record.getMaterialCost().add(record.getOtherCost()).doubleValue());
        managerServerApi.changeInventoryStatus(bo);
    }

    private InventoryRecord generateRecord(Long projectId) {
        InventoryRecord record = new InventoryRecord();
        Contract contract = contractServerApi.getDetailByProjectId(projectId);
        List<Journal> journals = journalMapper.listForInventory(projectId);
        Map<Boolean, List<Journal>> map = journals.stream().collect(Collectors.partitioningBy(e -> e.getRevenueOrExpense() == RevenueOrExpense.REVENUE));
        record.setExpenseDetail(items(map.get(false)));
        record.setRevenueDetail(items(map.get(true)));

        List<ProjectOutStockProduct> out = productServerApi.getForProjectOut(projectId);
        record.setMaterialCost(out.stream().reduce(BigDecimal.ZERO, (sum, o) -> sum.add(BigDecimal.valueOf(o.getPrice()).multiply(BigDecimal.valueOf(o.getCount()))), BigDecimal::add));
        record.setOtherCost(contract.getAccounting1().getDesignCost()
                .add(contract.getAccounting1().getExtendServiceCost())
                .add(contract.getAccounting1().getTotalCost()
                        .add(contract.getAccounting1().getHasGift() ? contract.getAccounting1().getGiftCost() : BigDecimal.ZERO)));

        record.setProjectId(projectId);
        record.setCreateId(CurrentUser.getUserId());
        record.setCreateTime(new Date());
        return record;
    }

    private InventoryItem[] items(List<Journal> records) {
        Map<Long, List<Journal>> map = records.stream().collect(Collectors.groupingBy(Journal::getType));
        List<InventoryItem> items = new ArrayList<>();
        for (Map.Entry<Long, List<Journal>> e : map.entrySet()) {
            BigDecimal money = BigDecimal.ZERO;
            for (Journal r : e.getValue()) {
                if (r.getStatus() == JournalStatus.PENDING_EXPENSE || r.getStatus() == JournalStatus.PENDING_REVENUE) {
                    throw new WrongOperationException(String.format("id为[%s]的记录尚未处理", r.getId()));
                }
                money = money.add(r.getActualMoney());
            }
            items.add(new InventoryItem(e.getKey(), money));
        }
        return items.toArray(new InventoryItem[items.size()]);
    }

    public InventoryRecord overview(Long projectId) {
        int status = managerServerApi.getProjectInventoryStatus(projectId);
        if (status != TaskStatus.InventoryStatus.HAVE_INVENTORY.getIntVerifier()) {
            throw new WrongOperationException("该项目不是已盘点状态");
        }

        return inventoryRecordMapper.getByProjectId(projectId);
    }
}
