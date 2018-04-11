package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.zifangdt.ch.base.enums.pair.NamedEnum;
import com.zifangdt.ch.base.util.ApplicationContextUtil;
import com.zifangdt.ch.base.util.ClassUtil;
import com.zifangdt.ch.base.util.JsonUtil;
import com.zifangdt.ch.base.validation.ConfiguredOption;
import com.zifangdt.ch.base.web.AbstractVerboseFetcher;
import com.zifangdt.ch.base.web.ConfigItemVerboseFetcher;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/10.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractExtraInfoPropertySerializer<A extends Annotation> extends JsonSerializer<Object> implements ContextualSerializer {

    private BeanProperty beanProperty;
    private boolean isCollection;
    private boolean isArray;
    private Class<?> clazz;
    private Class<A> annotationClass;

    private void setRequired(BeanProperty beanProperty, boolean isCollection, boolean isArray, Class<?> clazz) {
        this.beanProperty = beanProperty;
        this.isCollection = isCollection;
        this.isArray = isArray;
        this.clazz = clazz;
        setAnnotation(beanProperty.getAnnotation(annotationClass));
    }

    AbstractExtraInfoPropertySerializer() {
        this.annotationClass = (Class<A>) ClassUtil.getSuperclassActualTypes(getClass())[0];
    }

    private <T> Collection<T> toCollection(Class<T> idClass, Object value) {
        Collection<T> collection;
        if (isCollection) {
            collection = (Collection<T>) value;
        } else if (isArray) {
            collection = Arrays.asList((T[]) value);
        } else {
            collection = Collections.singletonList((T) value);
        }
        return collection;
    }

    private List<String> handleNamedEnums(Object value) {
        Collection<NamedEnum> enums = toCollection(NamedEnum.class, value);
        return enums.stream().map(NamedEnum::getName).collect(Collectors.toList());
    }

    protected List<?> finallyProcess(AbstractVerboseFetcher bean, List<?> verbose) {
        return verbose;
    }

    protected List<?> thenProcess(List<?> verbose) {
        return verbose;
    }

    private List<?> handle(Class<? extends AbstractVerboseFetcher> fetcherClass, Object value) {
        AbstractVerboseFetcher bean = ApplicationContextUtil.getBean(fetcherClass);
        Collection<?> ids = toCollection(bean.getIdClass(), value);
        List<?> verbose = bean.cachedList(ids);
        List<?> result = thenProcess(verbose.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        return finallyProcess(bean, result);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        provider.defaultSerializeValue(value, gen);

        if (JsonUtil.isSerializing()) {
            return;
        }

        List<?> result;
        if (clazz.isEnum() && NamedEnum.class.isAssignableFrom(clazz)) {
            result = handleNamedEnums(value);
        } else if (clazz == Long.class) {
            ConfiguredOption configuredOption = beanProperty.getAnnotation(ConfiguredOption.class);
            if (configuredOption != null) {
                result = handle(ConfigItemVerboseFetcher.class, value);
            } else {
                result = handle(getAnnotationTarget().getFetcherClass(), value);
            }
        } else {
            result = handle(getAnnotationTarget().getFetcherClass(), value);
        }

        if (result.isEmpty()) {
            return;
        }

        String fieldName = getAnnotationValue();
        if (fieldName.length() == 0) {
            String originName = beanProperty.getName();
            if (isCollection || isArray) {
                if (originName.endsWith("s")) {
                    originName = originName.substring(0, originName.length() - 1);
                }
                if (originName.endsWith("Id")) {
                    originName = originName.substring(0, originName.length() - 2);
                }
            } else {
                if (originName.endsWith("Id")) {
                    originName = originName.substring(0, originName.length() - 2);
                }
            }
            fieldName = generateFieldName(originName, isCollection || isArray);
        }

        write(gen, provider, fieldName, result);
    }

    protected void write(JsonGenerator gen, SerializerProvider provider, String fieldName, List<?> result) throws IOException {
        if (isCollection || isArray) {
            gen.writeFieldName(fieldName);
            provider.defaultSerializeValue(result, gen);
        } else if (result.size() == 1) {
            gen.writeObjectField(fieldName, result.get(0));
        }
    }

    protected abstract String generateFieldName(String originName, boolean isCollectionOrArray);

    protected abstract String getAnnotationValue();

    protected abstract JsonPropertyTarget getAnnotationTarget();

    protected abstract void setAnnotation(A annotation);

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            return prov.findNullValueSerializer(null);
        }
        JavaType type = property.getType();
        Class<?> clazz = type.getRawClass();
        boolean isCollection = type.hasGenericTypes() && Collection.class.isAssignableFrom(clazz);
        boolean isArray = clazz.isArray();
        if (isCollection) {
            clazz = type.containedType(0).getRawClass();
        } else if (isArray) {
            clazz = clazz.getComponentType();
        }

        setRequired(property, isCollection, isArray, clazz);

        return this;
    }
}
