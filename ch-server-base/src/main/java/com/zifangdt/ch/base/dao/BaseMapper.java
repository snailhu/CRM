package com.zifangdt.ch.base.dao;


import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.util.CurrentUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.Arrays;
import java.util.Date;

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
    default void updateAfterComparing(T stored, T received) {
        boolean flag = Arrays.stream(received.getClass().getDeclaredMethods()).filter(m -> {
            return m.getName().startsWith("get");
        }).anyMatch(m -> {
            try {
                Object result = m.invoke(received);
                return result != null && !result.equals(m.invoke(stored));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
        if (flag) {
            if (received instanceof AuditEntity<?>) {
                AuditEntity<?> r = (AuditEntity<?>) received;
                r.setModifyId(CurrentUser.getUserId());
                r.setModifyTime(new Date());
            }
            updateByPrimaryKeySelective(received);
        }
    }

}
