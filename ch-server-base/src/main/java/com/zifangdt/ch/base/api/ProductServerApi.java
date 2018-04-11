package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.product.PurchaseDetailDto;
import com.zifangdt.ch.base.dto.product.entity.OutStockChange;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.product.entity.Purchase;
import com.zifangdt.ch.base.dto.product.input.OutStockAutoProjectCreateDto;
import com.zifangdt.ch.base.dto.product.output.OutStockBrief;
import com.zifangdt.ch.base.dto.product.output.ProjectOutStockProduct;
import com.zifangdt.ch.base.dto.product.output.PurchaseBriefDto;
import com.zifangdt.ch.base.enums.ConfigType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient("product-server")
public interface ProductServerApi extends BaseConfigApi{

    @GetMapping("/purchase/{id}")
    PurchaseDetailDto getDetailOne(@PathVariable("id") Long id);

    @GetMapping("/purchase/base/{id}")
    Purchase getOne(@PathVariable("id") Long id);

    @GetMapping("/purchase/base/byIds")
    List<Purchase> baseGetMulti(@RequestParam("ids") Set<Long> ids);

    @GetMapping("/configs/steps")
    List<DetailOfFlowStep> findSteps(@RequestParam("configType") ConfigType configType);

    @PostMapping("/purchase/callbackAfterApproved")
    void callbackAfterApproved(@RequestBody ProcessInstance processInstance);

    /**
     * 获取项目出库单列表
     * @param projectId
     * @return
     */
    @GetMapping("/stock/out/project/{projectId}")
    List<OutStockBrief> getOutStockForProject(@PathVariable("projectId") Long projectId);

    /**
     * 自动项目材料出库
     * @return
     */
    @PostMapping("/stock/out/project/auto")
    OutStockChange createProjectOutAuto(@RequestBody OutStockAutoProjectCreateDto dto);

    /**
     * 获取多个产品
     * @param ids
     * @return
     */
    @GetMapping("/product/multi")
    List<Product> getMultiProducts(@RequestParam("ids") Set<Long> ids);

    @PostMapping("/news")
    void saveNews(News news);

    @PostMapping("/purchase/{id}")
    Map<String,Object> getDetailJson(@PathVariable("id") Long id, ProcessOverview processOverview);

    @GetMapping("/purchase/{id}/brief")
    PurchaseBriefDto getPurchaseBrief(@PathVariable("id") Long id);

    /**
     * 获取项目所有已出库产品信息
     * @param projectId
     * @return
     */
    @GetMapping(value = "/stock/out/project/{id}/products?for=internal")
    List<ProjectOutStockProduct> getForProjectOut(@PathVariable("id") Long projectId);

    /**
     * 获取所有仓库管理员工id
     * @return
     */
    @GetMapping("/configs/user/repoStaff")
    Set<Long> getAllRepoStaff();


    /**
     * 获取简单采购信息
     * @param id
     * @return
     */
    @GetMapping("/purchase/base/{id}")
    Purchase purchaseGetOne(@PathVariable("id") Long id);

    @GetMapping("/purchase/{id}/name?for=internal")
    String purchaseName(@PathVariable("id") Long id);

    @GetMapping("/news/byDate")
    List<News> newsByDate(@RequestParam("userId") Long userId, @RequestParam("date") String date);


    @GetMapping("/product/findByNum/{num}")
    Product findByNum(@PathVariable("num") String num);
}
