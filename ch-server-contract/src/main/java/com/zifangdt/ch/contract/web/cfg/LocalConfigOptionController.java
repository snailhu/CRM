package com.zifangdt.ch.contract.web.cfg;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.contract.service.cfg.ConfigItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@RestController
@RequestMapping("/options")
public class LocalConfigOptionController {

    @Autowired
    private ConfigItemService configItemService;

    @GetMapping("/procedures")
    public List<IdAndName> options(@RequestParam(value = "contractType", required = false) Long contractType) {
        return configItemService.findProcedures(contractType);
    }

    @GetMapping("/name")
    public String optionName(@RequestParam("optionId") Long optionId) {
        return configItemService.optionName(optionId);
    }

    @GetMapping("/names")
    public Map<Long, String> optionName(@RequestParam("optionIds") Set<Long> optionIds) {
        return configItemService.optionNames(optionIds);
    }
}
