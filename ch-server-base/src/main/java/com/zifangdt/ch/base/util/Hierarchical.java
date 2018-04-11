package com.zifangdt.ch.base.util;

import com.zifangdt.ch.base.dto.BaseEntity;

import java.util.List;

/**
 * Created by 袁兵 on 2017/9/26.
 */
public interface Hierarchical {
    <T extends BaseEntity<?> & Hierarchical> List<T> getChildren();
    Object getParentId();
}
