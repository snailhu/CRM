package com.zifangdt.ch.uaa.service;

import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.uaa.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 袁兵 on 2017/8/28.
 */
@Service
public class PermissionService extends BaseService<Permission, Long> {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }
}
