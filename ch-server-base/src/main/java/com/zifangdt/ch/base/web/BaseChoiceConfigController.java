package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.dto.BaseChoiceConfig;
import com.zifangdt.ch.base.enums.setting.ChoiceConfigEnum;
import com.zifangdt.ch.base.enums.setting.ChoiceEnum;
import com.zifangdt.ch.base.service.BaseChoiceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public class BaseChoiceConfigController<T extends BaseChoiceConfig<K>, K> {
    @Autowired
    BaseChoiceConfigService<T, K> choiceConfigService;

    /**
     * 获取配置详情
     * @param configEnum
     * @return
     */
    @GetMapping("/{choiceConfigEnum}")
    T getChoiceConfig(@PathVariable("choiceConfigEnum") ChoiceConfigEnum configEnum){
        return choiceConfigService.getOrCreate(configEnum);
    }

    /**
     * 更新配置
     * @param configEnum
     * @param choiceEnum
     * @return
     */
    @PutMapping("/{choiceConfigEnum}/{choiceEnum}")
    T update(@PathVariable("choiceConfigEnum") ChoiceConfigEnum configEnum,
                                @PathVariable("choiceEnum")ChoiceEnum choiceEnum){
        return choiceConfigService.updateChoice(configEnum, choiceEnum);
    }
}
