package com.zifangdt.ch.base.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.zifangdt.ch.base.converter.CustomEnumDeserializer;
import com.zifangdt.ch.base.converter.CustomEnumSerializer;

import java.io.IOException;

/**
 * Created by 袁兵 on 2018/1/8.
 */
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.setDateFormat(new ISO8601DateFormat());
        OBJECT_MAPPER.registerModule(
                new SimpleModule()
                        .addDeserializer(Enum.class, new CustomEnumDeserializer())
                        .addSerializer(Enum.class, new CustomEnumSerializer())
        );
        OBJECT_MAPPER.registerModule(new KotlinModule());
    }

    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("parse json failed", e);
        }
    }

    public static String stringify(Object value) {
        THREAD_LOCAL.set(new Object());
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("stringify json failed", e);
        } finally {
            THREAD_LOCAL.remove();
        }
    }

    public static boolean isSerializing() {
        return THREAD_LOCAL.get() != null;
    }
}
