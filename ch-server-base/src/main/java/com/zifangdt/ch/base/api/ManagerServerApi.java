package com.zifangdt.ch.base.api;


import com.zifangdt.ch.base.bo.InventoryBo;
import com.zifangdt.ch.base.bo.ManagerProjectBo;
import com.zifangdt.ch.base.bo.ProjectInfoBo;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.projectmanager.ProjectResult;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.product.OutStockStatus;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(value = "PROJECT-MANAGER-SERVER", qualifier = "baseManagerServerApi")
public interface ManagerServerApi {

    @PostMapping("/project/stopProject")
    void stopProject(@Valid @RequestBody ProjectResult projectResult);

    @GetMapping("/project/getProjectId/{contractId}")
    Map<String, Object> getByContractId(@PathVariable("contractId") Long contractId);
    @GetMapping("/configs/steps")
    List<DetailOfFlowStep> findSteps(@RequestParam("configType") ConfigType configType);


    @GetMapping("/project/getProjectOwner/{unitId}")
    Long getProjectOwner(@PathVariable("unitId") Long unitId );

    /**
     * 获取项目管家
     *
     * @return
     */
    @GetMapping("/configs/getProjectManager")
    Set<Long> getProjectManager();


    @PostMapping("/project/callbackAfterApproved")
    void callbackAfterApproved(@RequestBody ProcessInstance processInstance);


    @PostMapping("/project/create")
    Long createProject(@Valid @RequestBody ProjectInfoBo projectInfoBo);

//      @PostMapping("/project/create")
//    Long createProject(@Valid @RequestBody ProjectInfoBo projectInfoBo);

    /**
     * 项目盘点
     * @param inventoryBo
     * @return
     */
    @PostMapping("/project/projectInventory")
    int changeInventoryStatus(@Valid @RequestBody InventoryBo inventoryBo);

    /**
     * 根据id 获取项目信息
     * @param projectId
     * @return
     */

    @GetMapping("/project/getProjectInfo/{projectId}")
    ManagerProjectBo getProjectInfo(@PathVariable("projectId") Long projectId);

    /**
     * 结算工序
     * @param unitId
     */
    @PutMapping(path={"/project/changeUnitAccountStatus/{unitId}"})
    void changeUnitAccountStatus(@PathVariable("unitId") Long unitId);

    /**
     * 完成工序
     * @param unitId
     */
    @PutMapping(path={"/project/completeUnit/{unitId}"})
    void completeUnit(@PathVariable("unitId") Long unitId);


    /**
     * 关闭项目
     * @param projectResult
     */
    @PostMapping("/project/closeProject")
    void closeProject(@Valid @RequestBody ProjectResult projectResult);


    /**
     * 改变库存状态
     * @param unitId
     * @param outStockStatus
     */
    @PutMapping("/project/changeStoreStatus/{unitId}/{outStockStatus}")
   void changeStoreStatus(@PathVariable("unitId") Long unitId,@PathVariable("outStockStatus") OutStockStatus outStockStatus);


    /**
     * 存储工单编号
     * @param unitId
     * @param unitOrderNum
     */
    @PutMapping("/project/saveUnitNum/{unitId}/{unitOrderNum}")
    void saveUnitNum(
            @PathVariable("unitId") Long unitId,@PathVariable("unitOrderNum") String unitOrderNum);


    /**
     * 根据工序Id 获取项目详情
     * @param unitId
     * @return
     */
    @GetMapping("/project/{unitId}/getSchemeDetail")
    ManagerProjectBo getSchemeDetailByUnitId(@PathVariable("unitId") Long unitId);


    @PostMapping("/news")
    void saveNews(News news);

    /**
     * 获取项目盘点状态
     * @param projectId
     * @return
     */
    @GetMapping("/project/inventoryStatus/{projectId}")
    int getProjectInventoryStatus(@PathVariable("projectId") Long projectId);

    @GetMapping("/news/mine")
    List<News> mineNews(@RequestParam("date") String date);


    /**
     * 获取客户的项目数量
     * @param customerId
     * @return
     */
    @GetMapping("/project/customerProjectNum/{customerId}")
    Integer getCustomerProjectNum(@PathVariable("customerId") Long customerId);


    /**
     * 获取客户的交付项目数量
     * @param customerId
     * @return
     */
    @GetMapping("/project/customerHandlerProjectNum/{customerId}")
    Integer getCustomerHandlerProjectNum(@PathVariable("customerId") Long customerId);

    @GetMapping("/news/byDate")
    List<News> newsByDate(@RequestParam("userId") Long userId, @RequestParam("date") String date);
}