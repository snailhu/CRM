package com.zifangdt.ch.base.converter;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/11.
 */
public interface VerboseInfoPostProcessor<T> {

    List<T> postProcess(List<T> list);

    interface None
            extends VerboseInfoPostProcessor<Object> {
    }
}
