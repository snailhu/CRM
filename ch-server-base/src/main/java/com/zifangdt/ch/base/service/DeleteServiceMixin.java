package com.zifangdt.ch.base.service;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.exception.DataInvalidException;
import org.springframework.beans.factory.annotation.Autowired;

public interface DeleteServiceMixin {
    BaseMapper getMapper();

    default void delete(Object id){
        if (getMapper().selectByPrimaryKey(id) == null){
            throw new DataInvalidException("数据不存在");
        }
        getMapper().deleteByPrimaryKey(id);
    }
}
