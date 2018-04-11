package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.web.AbstractVerboseFetcher;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/10.
 */
public class NamedPropertySerializer extends AbstractExtraInfoPropertySerializer<NamedProperty> {

    private NamedProperty namedProperty;

    @Override
    protected void write(JsonGenerator gen, SerializerProvider provider, String fieldName, List<?> result) throws IOException {
        gen.writeStringField(fieldName, StringUtils.join(result, ","));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<?> finallyProcess(AbstractVerboseFetcher bean, List<?> verbose) {
        return verbose.stream().map(n -> bean.nameOf((BaseEntity) n)).collect(Collectors.toList());
    }

    @Override
    protected String generateFieldName(String originName, boolean isCollectionOrArray) {
        return originName + (isCollectionOrArray ? "Names" : "Name");
    }

    @Override
    protected String getAnnotationValue() {
        return namedProperty.value();
    }

    @Override
    protected JsonPropertyTarget getAnnotationTarget() {
        return namedProperty.target();
    }

    @Override
    protected void setAnnotation(NamedProperty annotation) {
        this.namedProperty = annotation;
    }

}
