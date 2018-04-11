package com.zifangdt.ch.base.web.cfg;

import com.zifangdt.ch.base.bo.BatchUpdateBo;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoice;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by 袁兵 on 2018/1/6.
 */
@RestController
@RequestMapping("/configs")
@ConditionalOnBean(AbstractConfigItemService.class)
public class ConfigItemController {
    @Autowired
    private AbstractConfigItemService configItemService;

    @GetMapping
    public Map<ConfigType, Object> all() {
        return configItemService.all();
    }

    @PostMapping("/{configType}")
    public void saveOrUpdate(@PathVariable("configType") ConfigType configType,
                             @RequestBody String json) {
        configItemService.saveOrUpdate(configType, json);
    }

    @GetMapping("/{configType}")
    public Object byType(@PathVariable("configType") ConfigType configType) {
        return configItemService.byType(configType);
    }

    @DeleteMapping("/{id}")
    public void deleteOption(@PathVariable("id") Long id) {
        configItemService.deleteOption(id);
    }

    @PutMapping("/{uuid}/approvers")
    public void updateApprovers(@PathVariable("uuid") String uuid, @Valid @RequestBody BatchUpdateBo bo) {
        configItemService.updateApprovers(uuid, bo);
    }

    @GetMapping("/steps")
    public List<DetailOfFlowStep> steps(@RequestParam("configType") ConfigType configType) {
        return configItemService.steps(configType);
    }

    @GetMapping("/perms")
    public DetailOfUserChoice perms(@RequestParam("configType") ConfigType configType) {
        return configItemService.perms(configType);
    }
}
