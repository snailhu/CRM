package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.quote.QuoteBill;
import com.zifangdt.ch.base.enums.ConfigType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("quote-server")
public interface QuoteServerApi {

    @PostMapping("/getOwnerId/{billId}")
     Long getOwnerId(@PathVariable("billId") Long billId);


    @GetMapping("/configs/steps")
    List<DetailOfFlowStep> findSteps(@RequestParam("configType") ConfigType configType);


    @PostMapping("/quote/callbackAfterApproved")
    void callbackAfterApproved(@RequestBody ProcessInstance processInstance);


    @PostMapping("/quote/ids")
    List<QuoteBill> getQuoteBillById(@RequestBody List<Long> billIds);

    @PostMapping("/news")
    void saveNews(@RequestBody News news);


    /**
     * 获取报价单详情
     * @param billId
     * @return
     */
    @GetMapping("/quote/getDetail/{billId}")
    QuoteBill getSimpleBillDetailById(@PathVariable("billId") Long billId);



    @PostMapping("/quote/getDetail/{billId}")
    Map<String,Object> getSimpleBillDetailJsonById(@PathVariable("billId") Long billId, @RequestBody ProcessOverview processOverview);


    /**
     * 获取客户的报价单数量
     * @param customerId
     * @return
     */
    @GetMapping("/quote/customerBillNum/{customerId}")
    Integer getCustomerBillNum(@PathVariable("customerId") Long customerId);

    @GetMapping("/quote/getDetail/{billId}/name?for=internal")
    String billName(@PathVariable("billId") Long billId);

    @GetMapping("/news/byDate")
    List<News> newsByDate(@RequestParam("userId") Long userId, @RequestParam("date") String date);
}