package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.dto.customer.setting.CustomerChoiceConfig;
import com.zifangdt.ch.base.dto.setting.ChoiceConfig;
import com.zifangdt.ch.base.dto.setting.UserOrga;
import com.zifangdt.ch.base.enums.setting.ChoiceConfigEnum;
import com.zifangdt.ch.base.enums.setting.UserConfigEnum;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(value = "customer-server")
public interface CustomerSettingServerApi {

    /**
     * 获取客户配置，数量配置
     *
     * @param choiceConfigEnum:
     * @return
     */
    @GetMapping("/number/{choiceConfigEnum}")
    Integer getConfigNum(@PathVariable("choiceConfigEnum") ChoiceConfigEnum choiceConfigEnum);

    /**
     * 获取客户配置，开关配置
     *
     * @param configEnum
     * @return
     */
    @GetMapping("/config/{choiceConfigEnum}")
    ChoiceConfig getChoiceConfig(@PathVariable("choiceConfigEnum") ChoiceConfigEnum configEnum);

    @GetMapping("/number/forCustomerPool")
    Map<String, Integer> getNumberForCustomerPool();

    @GetMapping("/config/forCustomerPool")
    Map<String, CustomerChoiceConfig> getConfigForCustomerPool();

    @GetMapping("/userConfig/{userConfigEnum}")
    List<UserOrga> getUserOrgasBy(@PathVariable("userConfigEnum") UserConfigEnum configEnum);

}
