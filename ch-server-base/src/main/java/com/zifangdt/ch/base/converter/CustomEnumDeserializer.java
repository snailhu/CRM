package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.zifangdt.ch.base.enums.pair.IntVerifierEnum;
import com.zifangdt.ch.base.util.ClassUtil;

import java.io.IOException;

/**
 * Created by 袁兵 on 2017/12/7.
 */
public class CustomEnumDeserializer extends JsonDeserializer<Enum> implements ContextualDeserializer {
    private Class<Enum> enumClass;

    public CustomEnumDeserializer() {
    }

    private CustomEnumDeserializer(Class<Enum> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken currentToken = p.getCurrentToken();
        if (currentToken.isNumeric()) {
            int value = p.getIntValue();
            if (IntVerifierEnum.class.isAssignableFrom(enumClass)) {
                return IntVerifierEnum.fromIntVerifier(enumClass, value);
            } else {
                return enumClass.getEnumConstants()[value];
            }
        } else {
            return ClassUtil.valueOfEnumIgnoreCase(enumClass, p.getText());
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctx, BeanProperty property) throws JsonMappingException {
        @SuppressWarnings("unchecked")
        Class<Enum> rawClass;
        if (property.getType().isCollectionLikeType()) {
            rawClass = (Class<Enum>) property.getType().getContentType().getRawClass();
        } else {
            rawClass = (Class<Enum>) property.getType().getRawClass();
        }
        return new CustomEnumDeserializer(rawClass);
    }

}
