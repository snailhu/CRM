package com.zifangdt.ch.base.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 袁兵 on 2017/12/26.
 */
@RestController
@RequestMapping("/enumBuster")
public class EnumBusterController {

    @Autowired
    private EnumBusterService enumBusterService;

    @GetMapping
    public <T extends Enum<T>> void modifyEnum(
            @RequestParam("clazz") Class<T> clazz,
            @RequestParam("name") String name,
            @RequestParam(value = "argTypes", required = false) Class[] classes,
            @RequestParam(value = "argValues", required = false) Object[] values) {

        enumBusterService.modifyEnum(clazz,name,classes,values);

    }

    @GetMapping("/dataEnum")
    public void modifyDataEnum(
            @RequestParam("type") int type,
            @RequestParam("key") Long key,
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "delete", required = false) String delete) {

        enumBusterService.modifyDataEnum(type, key, value, StringUtils.isNotEmpty(delete));
    }
}
