package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.zifangdt.ch.base.enums.DataEnum;
import com.zifangdt.ch.base.util.UserNameHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.zifangdt.ch.base.util.WebUtil.isFeignRequest;

/**
 * Created by 袁兵 on 2017/9/12.
 */
@Deprecated
public class CustomBeanSerializerModifier extends BeanSerializerModifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanSerializerModifier.class);

    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        LOGGER.debug("###modifySerializer for BeanDescription:{}",beanDesc.getBeanClass());
        if (isFeignRequest()) {
            return serializer;
        }
        return new NameGeneratingSerializer((JsonSerializer<Object>) serializer, null, null);
    }

    private class PropertyNameTransformer extends NameTransformer {

        private String newName;

        PropertyNameTransformer(String newName) {
            this.newName = newName;
        }

        @Override
        public String transform(String name) {
            return newName;
        }

        @Override
        public String reverse(String transformed) {
            return null;
        }
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        if (isFeignRequest()) {
            return beanProperties;
        }
        Map<String, BeanPropertyWriter> map = new HashMap<>();
        Map<String, BeanPropertyWriter> properties = beanProperties.stream().filter(writer -> {
            JsonReplacer jsonReplacer = writer.getAnnotation(JsonReplacer.class);
            if (jsonReplacer != null) {
                map.put(jsonReplacer.value(), writer.rename(new PropertyNameTransformer(jsonReplacer.value())));
                return false;
            }
            return true;
        }).collect(Collectors.toMap(BeanPropertyWriter::getName, Function.identity()));

        if (!map.isEmpty()) {
            properties.putAll(map);
        }

        return new ArrayList<>(properties.values());
    }

    private class NameGeneratingSerializer extends JsonSerializer<Object> implements ContextualSerializer {
        private final BeanProperty beanProperty;
        private final JsonSerializer<Object> serializer;
        private final NameGenerator annotation;

        public NameGeneratingSerializer(JsonSerializer<Object> serializer, BeanProperty beanProperty, NameGenerator annotation) {
            this.beanProperty = beanProperty;
            this.serializer = serializer;
            this.annotation = annotation;
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            LOGGER.debug("serializing for {} with value {}...", beanProperty, value);
            serializer.serialize(value, gen, serializers);
            if (value != null && beanProperty != null && annotation != null) {
                LOGGER.debug("generating name for {} with value {}...", beanProperty, value);
                String name = null;
                DataEnum[] dataEnums = annotation.value();
                if (dataEnums.length > 0) {
                    name = DataEnum.getValueFromMultiInstances((Long) value, dataEnums);
                } else {
                    name = UserNameHolder.getName((Long) value);
                }
                if (name != null) {
                    String fieldName = beanProperty.getName();
                    if (fieldName.endsWith("Id")) {
                        fieldName = fieldName.substring(0, fieldName.length() - 2);
                    }
                    gen.writeStringField(fieldName + "Name", name);
                    LOGGER.debug("generating finished...name:{}", name);
                }
            }
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
            LOGGER.debug("###createContextual for BeanProperty:{}",property);
            if (property != null) {
                NameGenerator annotation = property.getAnnotation(NameGenerator.class);
                if (annotation != null) {
                    return new NameGeneratingSerializer(serializer, property, annotation);
                }
            }
            return serializer;
        }
    }

}
