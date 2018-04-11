package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.SupportFeignVerbose;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.util.WebUtil;
import com.zifangdt.ch.base.validation.ConfiguredOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.*;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomResponseBodyAdvice.class);
    private static final String METHOD_NAME_SAVE_PREFIX = "save";

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Autowired
    private List<AbstractVerboseFetcher> fetchers;
    @Autowired
    private ApplicationContext context;

    public CustomResponseBodyAdvice() {
        System.out.println("################CustomResponseBodyAdvice");
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (WebUtil.isFeignRequest() && !returnType.hasMethodAnnotation(SupportFeignVerbose.class)) {
            return false;
        }
        Class<?> returnClazz = returnType.getMethod().getReturnType();
        return returnClazz == Object.class || returnClazz != RestResult.class && !returnClazz.isAssignableFrom(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Class<?> returnClass = returnType.getMethod().getReturnType();
        if (returnClass == void.class) {
            return RestResult.success();
        } else if (returnClass == String.class) {
            ServletServerHttpResponse res = (ServletServerHttpResponse) response;
            HttpStatus httpStatus = HttpStatus.valueOf(res.getServletResponse().getStatus());
            try {
                converter.write(RestResult.of(httpStatus, body), MediaType.APPLICATION_JSON_UTF8, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        if (body == null) {
            return RestResult.success();
        }
        String methodName = returnType.getMethod().getName();

        if (methodName.startsWith(METHOD_NAME_SAVE_PREFIX)) {
            ServletServerHttpRequest req = (ServletServerHttpRequest) request;
            String url = req.getServletRequest().getHeader(Constants.HTTP_HEADER_REQUEST_URL);
            URI uri = UriComponentsBuilder.fromUriString(url).path("/" + body).build().toUri();
            response.getHeaders().setLocation(uri);
            response.setStatusCode(HttpStatus.CREATED);
            return RestResult.success();
        }


        Set<Field> excluded = filteredFields(returnType);
        fetchers.forEach(AbstractVerboseFetcher::clearCollectedIds);
        travel(body, excluded);
        fetchers.forEach(AbstractVerboseFetcher::collect);

        if (WebUtil.isFeignRequest() && returnType.hasMethodAnnotation(SupportFeignVerbose.class)) {
            return body;
        } else {
            return RestResult.success(body);
        }
    }

    private Set<Field> filteredFields(MethodParameter returnType) {
        Class<?> returnClass = returnType.getMethod().getReturnType();
        Set<Field> excluded = new HashSet<>();
        JsonPropertyFilter[] filters = null;
        if (returnType.hasMethodAnnotation(JsonPropertyFilter.class)) {
            filters = new JsonPropertyFilter[]{returnType.getMethodAnnotation(JsonPropertyFilter.class)};
        } else if (returnType.hasMethodAnnotation(JsonPropertyFilters.class)) {
            filters = returnType.getMethodAnnotation(JsonPropertyFilters.class).value();
        }
        if (filters != null) {
            for (JsonPropertyFilter filter : filters) {
                Class<?> finalClass = returnClass;
                if (filter.entityClass() != Void.class) {
                    finalClass = filter.entityClass();
                }
                if (isOwnClass(finalClass)) {
                    for (String n : filter.value()) {
                        Field field = ReflectionUtils.findField(finalClass, n);
                        if (field != null) {
                            excluded.add(field);
                        }
                    }
                }
            }
        }
        return excluded;
    }

    private void travel(Object object, Set<Field> excluded) {
        if (object == null) {
            return;
        }
        Class<?> clazz = object.getClass();
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> collection = (Collection<?>) object;
            if (!CollectionUtils.isEmpty(collection)) {
                for (Object item : collection) {
                    travel(item, excluded);
                }
            }
        } else if (clazz.isArray()) {
            Object[] array = (Object[]) object;
            for (Object item : array) {
                travel(item, excluded);
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map<?, ?> map = (Map<?, ?>) object;
            if (!CollectionUtils.isEmpty(map)) {
                for (Object item : map.values()) {
                    travel(item, excluded);
                }
            }
        } else if (isOwnClass(clazz)) {
            ReflectionUtils.doWithFields(clazz, field -> {
                        if (Modifier.isStatic(field.getModifiers())
                                || Modifier.isAbstract(field.getModifiers())
                                || field.getName().startsWith("this$")) {
                            return;
                        }
                        Object value = null;
                        try {
                            field.setAccessible(true);
                            value = field.get(object);
                        } catch (Exception e) {
                            LOGGER.error("get value failed.", e);
                        }
                        if (value == null) {
                            return;
                        }
                        if (excluded.contains(field)) {
                            field.set(object, null);
                            return;
                        }

                        if (isAnnotatedField(field)) {
                            NamedProperty namedProperty = field.getAnnotation(NamedProperty.class);
                            if (namedProperty != null) {
                                addId(namedProperty.target(), field, value);
                            }

                            VerboseProperty verboseProperty = field.getAnnotation(VerboseProperty.class);
                            if (verboseProperty != null) {
                                addId(verboseProperty.target(), field, value);
                            }
                        } else if (isNonSingleField(field) || isOwnClass(field.getType())) {
                            travel(value, excluded);
                        }
                    }
            );
        }
    }

    private boolean isOwnClass(Class<?> type) {
        return !type.isPrimitive() && !type.isEnum() && !type.isAnnotation() && type.getPackage().getName().startsWith(Constants.PACKAGE_PREFIX);
    }

    private boolean isAnnotatedField(Field field) {
        return field.isAnnotationPresent(NamedProperty.class)
                || field.isAnnotationPresent(VerboseProperty.class);
    }

    private boolean isNonSingleField(Field field) {
        return Collection.class.isAssignableFrom(field.getType())
                || field.getType().isArray()
                || Map.class.isAssignableFrom(field.getType());
    }

    @SuppressWarnings("unchecked")
    private void addId(JsonPropertyTarget target, Field field, Object value) {
        Class<?> fieldType = field.getType();
        if (field.isAnnotationPresent(ConfiguredOption.class)) {
            context.getBean(ConfigItemVerboseFetcher.class).addCollectedId(fieldType, value);
        } else if (target != JsonPropertyTarget.DEFAULT) {
            context.getBean(target.getFetcherClass()).addCollectedId(fieldType, value);
        }
    }

}
