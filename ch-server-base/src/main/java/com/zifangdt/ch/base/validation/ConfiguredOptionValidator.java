package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.bo.cfg.contract.DetailOfContractType;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConfiguredOptionValidator implements ConstraintValidator<ConfiguredOption, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguredOptionValidator.class);

    @Autowired(required = false)
    private AbstractConfigItemService configItemService;

    private ConfigType[] configTypes;

    @Override
    public void initialize(ConfiguredOption constraintAnnotation) {
        this.configTypes = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (configItemService == null) {
            LOGGER.error("spring bean of type AbstractConfigItemService not found in current module");
            return false;
        }
        List<DetailOfOption> options = configItemService.configItems(new HashSet<>(Arrays.asList(configTypes)));
        if (CollectionUtils.isEmpty(options)) {
            LOGGER.error("no options found for " + Arrays.toString(configTypes));
            return false;
        }
        Map<Long, DetailOfOption> map = options.stream().collect(Collectors.toMap(DetailOfOption::getId, Function.identity()));
        Set<Long> ids = map.keySet();

        Class<?> clazz = value.getClass();
        if (Collection.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked")
            Collection<Long> collection = (Collection<Long>) value;
            if (!collection.stream().allMatch(ids::contains)) {
                return false;
            }
            if (configTypes.length == 1 && configTypes[0] == ConfigType.contractTypes) {
                List<Long> excludes = collection.stream()
                        .map(map::get)
                        .filter(o -> ((DetailOfContractType) o).getMutuallyExclusive())
                        .map(DetailOfOption::getId)
                        .collect(Collectors.toList());
                if (excludes.size() > 1) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("存在互斥值" + excludes).addConstraintViolation();
                    return false;
                }
            }
            return true;
        } else {
            return clazz == Long.class && ids.contains(value);
        }
    }
}
