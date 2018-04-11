package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 袁兵 on 2018/1/11.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = VerbosePropertySerializer.class)
public @interface VerboseProperty {
    String value() default "";

    JsonPropertyTarget target() default JsonPropertyTarget.DEFAULT;

    @SuppressWarnings("rawtypes")
    Class<? extends VerboseInfoPostProcessor> postProcessor() default VerboseInfoPostProcessor.None.class;

}
