package com.zifangdt.ch.finance.web;

import com.zifangdt.ch.finance.service.ConfigItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@RestController
@RequestMapping("/configs")
public class LocalConfigItemController {

    @Autowired
    private ConfigItemService configItemService;

    @GetMapping("/financialStaff")
    public Set<Long> financialStaff() {
        return configItemService.financialStaff();
    }

}
