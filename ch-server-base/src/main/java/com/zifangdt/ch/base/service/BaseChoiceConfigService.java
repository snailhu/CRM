package com.zifangdt.ch.base.service;

import com.zifangdt.ch.base.dto.BaseChoiceConfig;
import com.zifangdt.ch.base.enums.setting.ChoiceConfigEnum;
import com.zifangdt.ch.base.enums.setting.ChoiceEnum;
import com.zifangdt.ch.base.exception.DataInvalidException;

public abstract class BaseChoiceConfigService<T extends BaseChoiceConfig<K>, K> extends BaseService<T, K>{

    public abstract T getOrCreate(ChoiceConfigEnum choiceConfigEnum);

    public T updateChoice(ChoiceConfigEnum choiceConfigEnum, ChoiceEnum choiceEnum){
        if (!choiceConfigEnum.getAvailableChoice().contains(choiceEnum)){
            throw new DataInvalidException(choiceEnum + " is not valid for " + choiceConfigEnum);
        } else {
            T choiceConfig = getOrCreate(choiceConfigEnum);
            choiceConfig.setChoice(choiceEnum);
            updateInternal(choiceConfig);
            return choiceConfig;
        }
    }
}