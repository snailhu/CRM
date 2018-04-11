package com.zifangdt.ch.uaa.web;

import com.zifangdt.ch.base.dto.uaa.Role;
import com.zifangdt.ch.uaa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by 袁兵 on 2017/8/30.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> all() {
        return roleService.findAll();
    }

    @PostMapping
    public Long save(@Valid @RequestBody Role role) {
        roleService.save(role);
        return role.getId();
    }

    @GetMapping("/{id}")
    public Role getDetail(@PathVariable("id") Long id) {
        return roleService.getDetail(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody Role role) {
        roleService.update(id, role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        roleService.delete(id);
    }
}
