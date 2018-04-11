package com.zifangdt.ch.base.converter;

import com.zifangdt.ch.base.enums.pair.IntVerifierEnum;
import com.zifangdt.ch.base.util.ClassUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Created by 袁兵 on 2018/1/13.
 */
public class CustomStringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnum(targetType);
    }

    private class StringToEnum<T extends Enum<T>> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            source = source.trim();
            if (IntVerifierEnum.class.isAssignableFrom(enumType)) {
                try {
                    int intVerifier = Integer.parseInt(source);
                    return IntVerifierEnum.fromIntVerifier(enumType,intVerifier);
                } catch (NumberFormatException e) {
                    return ClassUtil.valueOfEnumIgnoreCase(enumType, source);
                }
            } else {
                return ClassUtil.valueOfEnumIgnoreCase(enumType, source);
            }
        }
    }
}
