package com.zifangdt.ch.finance.web;

import com.zifangdt.ch.base.dto.finance.InventoryRecord;
import com.zifangdt.ch.finance.bo.InventoryClearingBo;
import com.zifangdt.ch.finance.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 袁兵 on 2018/2/7.
 */
@RestController
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{projectId}/clearings")
    public List<InventoryClearingBo> clearings(@PathVariable("projectId") Long projectId) {
        return inventoryService.clearingBos(projectId);
    }

    @GetMapping("/{projectId}/details")
    public Map<String, Object> details(@PathVariable("projectId") Long projectId) {
        return inventoryService.details(projectId);
    }

    @PutMapping("/{projectId}/start")
    public void start(@PathVariable("projectId") Long projectId) {
        inventoryService.startInventory(projectId);
    }

    @PutMapping("/{projectId}")
    public void doInventory(@PathVariable("projectId") Long projectId) {
        inventoryService.doInventory(projectId);
    }

    @GetMapping("/{projectId}/overview")
    public InventoryRecord overview(@PathVariable("projectId") Long projectId) {
        return inventoryService.overview(projectId);
    }
}
