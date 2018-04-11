package com.zifangdt.ch.uaa.web;

import com.zifangdt.ch.uaa.bo.OrganizationUpdateBo;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.uaa.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by 袁兵 on 2017/8/30.
 */
@RestController
@RequestMapping("/organs")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{id}")
    public Organization get(@PathVariable("id") Long id) {
        return organizationService.getDetail(id);
    }

    @GetMapping("/{id}/userIds")
    public List<Long> getWithUser(@PathVariable("id") Long id) {
        return organizationService.getWithUser(id);
    }

    @GetMapping
    public List<Organization> organsWithUser() {
        return organizationService.findTreeWithUser();
    }

    @PostMapping
    public Long save(@Valid @RequestBody Organization organization) {
        organizationService.save(organization);
        return organization.getId();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        organizationService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody OrganizationUpdateBo organizationUpdateBo) {
        organizationService.update(id, organizationUpdateBo);
    }

    @PutMapping("/{id}/parent/{newParentId}")
    public void transfer(@PathVariable("id") Long id, @PathVariable("newParentId") Long newParentId) {
        organizationService.transfer(id, newParentId);
    }

    @GetMapping("/all")
    List<Organization> findAllOrgans() {
        return organizationService.findAll();
    }

    @GetMapping("/mixed")
    public List<Organization> organsWithMixed() {
        return organizationService.findTreeWithMixed();
    }
}
