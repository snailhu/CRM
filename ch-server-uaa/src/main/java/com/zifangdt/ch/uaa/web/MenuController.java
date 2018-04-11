package com.zifangdt.ch.uaa.web;

import com.zifangdt.ch.base.dto.uaa.Menu;
import com.zifangdt.ch.base.enums.Terminal;
import com.zifangdt.ch.uaa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 袁兵 on 2018/2/3.
 */
@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/mine")
    public List<Menu> menus(@RequestParam("terminal") Terminal terminal) {
        return menuService.findForCurrentUser(terminal);
    }
}
