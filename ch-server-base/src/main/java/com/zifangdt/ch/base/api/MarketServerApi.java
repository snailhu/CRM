package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.dto.market.PromotionBo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MARKET-SERVER")
public interface MarketServerApi {
    @GetMapping("/promotion/{id}")
    PromotionBo getDetail(@PathVariable("id") Long id);
}
