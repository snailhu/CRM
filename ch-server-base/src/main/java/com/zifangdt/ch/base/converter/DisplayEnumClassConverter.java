package com.zifangdt.ch.base.converter;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;
import org.reflections.Reflections;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DisplayEnumClassConverter implements Converter<String, Class<? extends Enum<? extends DisplayableEnum>>>{
    private static Set<Class<? extends DisplayableEnum>> enums = new HashSet<>();
    static {
        Reflections reflections = new Reflections("com.zifangdt.ch");
        enums = reflections.getSubTypesOf(DisplayableEnum.class);
    }

    @Override
    public Class<? extends Enum<? extends DisplayableEnum>> convert(String source) {
        for (Class<? extends DisplayableEnum> enum1: enums) {
            if (enum1.isEnum()) {
                if (enum1.getSimpleName().equals(source)) {
                    return (Class<? extends Enum<? extends DisplayableEnum>>) enum1;
                }
            }
        }
        throw new RuntimeException("can't find suitable enum class");
    }
}
