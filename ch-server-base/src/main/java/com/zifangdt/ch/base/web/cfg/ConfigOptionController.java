package com.zifangdt.ch.base.web.cfg;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/9.
 */
@RestController
@RequestMapping("/options")
@ConditionalOnBean(AbstractConfigItemService.class)
public class ConfigOptionController {
    @Autowired
    private AbstractConfigItemService configItemService;

    @GetMapping("/{configType}")
    public List<IdAndName> options(@PathVariable("configType") ConfigType configType) {
        return configItemService.options(configType);
    }

}
