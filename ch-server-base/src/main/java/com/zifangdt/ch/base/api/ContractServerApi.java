package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.BooleanBo;
import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoice;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.enums.ConfigType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient("contract-server")
public interface ContractServerApi {

    @GetMapping("/contracts/byIds")
    List<Contract> getDetails(@RequestParam("ids") Set<Long> ids);

    @PostMapping("/news")
    void saveNews(News news);

    @GetMapping("/configs/steps")
    List<DetailOfFlowStep> findSteps(@RequestParam("configType") ConfigType configType);

    @GetMapping("/configs/perms")
    DetailOfUserChoice findPerms(@RequestParam("configType") ConfigType configType);

    @GetMapping("/contracts/{id}")
    Contract detail(@PathVariable("id") Long id);

    @PostMapping("/contracts/callbackAfterApproved")
    void callbackAfterApproved(@RequestBody ProcessInstance processInstance);

    @PostMapping("/contracts/{id}")
    Map<String, Object> detailJson(@PathVariable("id") Long id, @RequestBody ProcessOverview processOverview);

    @GetMapping("/options/procedures")
    List<IdAndName> options(@RequestParam(value = "contractType", required = false) Long contractType);

    @GetMapping("/contracts/{id}/callbackForContract?for=internal")
    void callbackForContract(@PathVariable("id") Long id,
                             @RequestParam("contractId") Long contractId,
                             @RequestParam("add") boolean add);

    @GetMapping("/options/name")
    String optionName(@RequestParam("optionId") Long optionId);

    @GetMapping("/contracts/byProjectId?for=internal")
    Contract getDetailByProjectId(@RequestParam("projectId") Long projectId);

    @GetMapping("/contracts/productSale/{id}")
    Long getProductSaleCount(@PathVariable("id") Long productId);

    @GetMapping("/options/names")
    Map<Long, String> optionNames(@RequestParam("optionIds") Set<Long> optionIds);

    @GetMapping("/news/mine")
    List<News> mineNews(@RequestParam("date") String date);

    @GetMapping("/contracts/{id}/withCustomer?for=internal")
    Map<String, Object> getJsonWithCustomer(@PathVariable("id") Long contractId);

    @GetMapping("/contracts/existsCustomer?for=internal")
    BooleanBo existsCustomer(@RequestParam("customerId") Long customerId);

    @GetMapping("/contracts/signedCustomerIds?for=internal")
    List<Long> findSignedCustomerIdsInDays(@RequestParam("days") int days);

    @GetMapping("/contracts/countByCustomerId?for=internal")
    long countByCustomerId(@RequestParam("customerId") Long customerId);

    @GetMapping("/contracts/{id}/printNumber?for=internal")
    String printNumber(@PathVariable("id") Long id);

    @GetMapping("/contracts/repurchasedCustomerIds?for=internal")
    List<Long> findRepurchasedCustomerIds();

    @GetMapping("/news/byDate")
    List<News> newsByDate(@RequestParam("userId") Long userId, @RequestParam("date") String date);

    @GetMapping("/contracts/{id}/simple?for=internal")
    Contract simple(@PathVariable("id") Long id);

    @GetMapping("/options/contractTypes")
    List<IdAndName> contractTypes();
}
