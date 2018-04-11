package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.util.ClassUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/13.
 */
public interface PairedEnum extends IntVerifierEnum, NamedEnum {

    Map<String, List<IdAndName>> PATH_TO_ENUM = new HashMap<String, List<IdAndName>>() {
        private static final long serialVersionUID = 7225936522732666139L;

        {
            Set<Class<?>> classes = ClassUtil.implementingClassesOf(PairedEnum.class, "com.zifangdt.ch.base.enums.pair");
            for (Class<?> clazz : classes) {
                if (clazz.isEnum()) {
                    put(
                            StringUtils.uncapitalize(clazz.getSimpleName()),
                            Arrays.stream(clazz.getEnumConstants())
                                    .map(e -> {
                                        PairedEnum pairedEnum = (PairedEnum) e;
                                        return new IdAndName((long) pairedEnum.getIntVerifier(), pairedEnum.getName());
                                    })
                                    .collect(Collectors.toList()));
                }
            }


        }
    };

    static List<IdAndName> fromPath(String path) {
        List<IdAndName> list = PATH_TO_ENUM.get(path);
        return list == null ? new ArrayList<>() : list;
    }
}
