package com.zifangdt.ch.uaa.web;

import com.zifangdt.ch.base.dto.uaa.Menu;
import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.uaa.service.MenuService;
import com.zifangdt.ch.uaa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 袁兵 on 2017/8/29.
 */
@RestController
@RequestMapping("/perms")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Menu> withMenu() {
        return menuService.findAllWithPermission();
    }

    @GetMapping(params = "for=internal")
    public List<Permission> all() {
        return permissionService.findAll();
    }

}
