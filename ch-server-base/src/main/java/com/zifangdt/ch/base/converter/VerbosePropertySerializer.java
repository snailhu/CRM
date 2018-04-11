package com.zifangdt.ch.base.converter;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.util.ClassUtil;
import com.zifangdt.ch.base.web.ConfigItemVerboseFetcher;
import com.zifangdt.ch.base.web.AbstractVerboseFetcher;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/11.
 */
public class VerbosePropertySerializer extends AbstractExtraInfoPropertySerializer<VerboseProperty> {

    private VerboseProperty verboseProperty;

    @Override
    protected String generateFieldName(String originName, boolean isCollectionOrArray) {
        return originName + (isCollectionOrArray ? "List" : "Detail");
    }

    @Override
    protected String getAnnotationValue() {
        return verboseProperty.value();
    }

    @Override
    protected JsonPropertyTarget getAnnotationTarget() {
        return verboseProperty.target();
    }

    @Override
    protected void setAnnotation(VerboseProperty annotation) {
        this.verboseProperty = annotation;
    }

    @Override
    protected List<?> thenProcess(List<?> verbose) {
        Class<? extends VerboseInfoPostProcessor> clazz = verboseProperty.postProcessor();
        if (clazz != VerboseInfoPostProcessor.None.class) {
            @SuppressWarnings("unchecked")
            List<?> processed = ClassUtil.newInstance(clazz).postProcess(verbose);
            return processed;
        }
        return verbose;
    }

    @Override
    protected List<?> finallyProcess(AbstractVerboseFetcher bean, List<?> verbose) {
        if (bean.getClass() == ConfigItemVerboseFetcher.class) {
            return verbose.stream().map(n -> {
                ConfigItem item = (ConfigItem) n;
                return new IdAndName(item.getId(), ((DetailOfOption) item.getDetail()).getName());
            }).collect(Collectors.toList());
        }
        return super.finallyProcess(bean, verbose);
    }
}
