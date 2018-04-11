package com.zifangdt.ch.base.util;

import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by 袁兵 on 2017/9/12.
 */
public class ClassUtil extends ReflectionUtils {

    private static final String MODIFIERS_FIELD = "modifiers";

    private static final ReflectionFactory reflection =
            ReflectionFactory.getReflectionFactory();

    public static void setStaticFinalField(
            Field field, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        // 获得 public 权限
        field.setAccessible(true);
        // 将modifiers域设为非final,这样就可以修改了
        Field modifiersField =
                Field.class.getDeclaredField(MODIFIERS_FIELD);
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);
        // 去掉 final 标志位
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);
        FieldAccessor fa = reflection.newFieldAccessor(
                field, false
        );
        fa.set(null, value);
    }

    public static Class<?>[] getSuperclassActualTypes(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        Type[] ts = pt.getActualTypeArguments();
        Class<?>[] result = new Class[ts.length];
        for (int i = 0; i < ts.length; i++) {
            result[i] = (Class<?>) ts[i];
        }
        return result;
    }

    public static Class<?> getFirstSuperInterfaceActualTypes(Class<?> clazz) {
        ParameterizedType pt = (ParameterizedType) clazz.getGenericInterfaces()[0];
        Type[] ts = pt.getActualTypeArguments();
        return (Class<?>) ts[0];
    }

    public static Class<?> getFieldActualType(Field field) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }


    public static Method selfMethod(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("new instance failed.", e);
        }
    }

    public static <T extends Enum<T>> T valueOfEnumIgnoreCase(Class<T> clazz, String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (T e : clazz.getEnumConstants()) {
            if (e.name().equalsIgnoreCase(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException("can not parse string " + name + " to enum " + clazz);
    }

    public static Set<Class<?>> implementingClassesOf(Class<?> interfaceClass, String... packages) {
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException();
        }
        return findCandidateClasses(clazz -> interfaceClass.isAssignableFrom(clazz) && !clazz.isInterface(), packages);
    }

    public static Set<Class<?>> findCandidateClasses(Predicate<Class<?>> predicate, String... packages) {
        if (packages.length == 0) {
            throw new IllegalArgumentException();
        }
        Set<Class<?>> set = new HashSet<>();
        ResourceLoader resourceLoader = null;
        CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourceLoader);
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        for (String packageName : packages) {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(packageName)) + "/**/*.class";
            try {
                Resource[] resources = resolver.getResources(packageSearchPath);
                for (Resource resource : resources) {
                    MetadataReader metadataReader = readerFactory.getMetadataReader(resource);
                    Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
                    if (predicate.test(clazz)) {
                        set.add(clazz);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        return set;
    }
}
