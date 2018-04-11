package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.enums.DataEnum;
import com.zifangdt.ch.base.util.EnumBuster;
import org.springframework.stereotype.Service;

/**
 * Created by 袁兵 on 2017/12/26.
 */
@Service
public class EnumBusterService {

    public <T extends Enum<T>> void modifyEnum(
            Class<T> clazz,
            String name,
            Class[] classes,
            Object[] values) {

        EnumBuster<T> buster = new EnumBuster<>(clazz);
        if (classes != null) {
            T t = buster.make(name, clazz.getEnumConstants().length, classes, values);
            buster.addByValue(t);
        } else {
            T t = buster.make(name, clazz.getEnumConstants().length);
            buster.addByValue(t);
        }

    }

    public void modifyDataEnum(int type, Long key, String value, boolean delete) {
        DataEnum dataEnum = DataEnum.getByType(type);
        if (dataEnum != null) {
            if (delete) {
                dataEnum.removePair(key);
            } else {
                dataEnum.addOrReplacePair(key, value);
            }
        }
    }
}
