package com.zifangdt.ch.uaa.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.uaa.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2017/8/24.
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findAll();

}
