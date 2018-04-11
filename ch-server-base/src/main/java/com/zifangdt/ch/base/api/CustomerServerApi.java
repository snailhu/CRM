package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.CustomerCreateBo;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.customer.Partner;
import com.zifangdt.ch.base.dto.customer.SalesClueNews;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.NewsType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 袁兵 on 2018/1/24.
 */
@FeignClient("customer-server")
public interface CustomerServerApi {

    @GetMapping("/customers/byIds")
    List<Customer> findList(@RequestParam("ids") List<Long> ids);

    @PostMapping("/news")
    void saveNews(@RequestBody News news);

    @GetMapping("/customers/{id}")
    Map<String, Object> detailJson(@PathVariable("id") Long id);

    @GetMapping("/customers/{id}/sourceRelationName?for=internal")
    String sourceRelationName(@PathVariable("id") Long id, @RequestParam(value = "sourceRelationId", required = false) Long sourceRelationId);

    @GetMapping("/customers/{id}/callbackForContract?for=internal")
    void callbackForContract(@PathVariable("id") Long id,
                             @RequestParam("contractId") Long contractId,
                             @RequestParam("add") boolean add);

    @GetMapping("/customers/{id}/callbackForApprovedContract?for=internal")
    void callbackForApprovedContract(@PathVariable("id") Long id);

    @PostMapping("/clues/news?for=internal")
    void addSalesClueNews(@RequestBody SalesClueNews news);

    @GetMapping("/news/mine")
    List<News> mineNews(@RequestParam("date") String date);

    @PostMapping("/customers")
    Long save(@RequestBody CustomerCreateBo customerCreateBo);

    @GetMapping("/configs/{configType}")
    List<Map<String, Object>> byType(@PathVariable("configType") ConfigType configType);

    default Customer getOneCustomer(Long customerId) {
        List<Customer> customerList = findList(Collections.singletonList(customerId));
        if (customerList.isEmpty()) throw new DataInvalidException("id=" + customerId + "的客户不存在或者已经失效");
        return customerList.get(0);
    }

    @PutMapping("/clues/{id}/transform?for=internal")
    void transformFromClue(@PathVariable("id") Long id,
                           @RequestParam("type") NewsType type,
                           @RequestParam("sourceName") String sourceName);

    @GetMapping("/news/byDate")
    List<News> newsByDate(@RequestParam("userId") Long userId, @RequestParam("date") String date);

    @GetMapping("/customers/{id}/partnerId?for=internal")
    Long getPartnerId(@PathVariable("id") Long id);

    @GetMapping("/partners/{id}")
    Partner getPartner(@PathVariable("id") Long id);
}
