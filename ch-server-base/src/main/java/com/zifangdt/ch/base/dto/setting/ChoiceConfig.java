package com.zifangdt.ch.base.dto.setting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.setting.ChoiceConfigEnum;
import com.zifangdt.ch.base.enums.setting.ChoiceEnum;

import javax.persistence.Table;
import java.util.List;

@Table(name = "cfg_choice_config")
@JsonIgnoreProperties(value={ "choices" }, allowGetters=true)
public class ChoiceConfig extends AuditEntity<Long>{
    private ChoiceConfigEnum config;

    private String description;

    private ChoiceEnum choice;

    public ChoiceConfigEnum  getConfig() {
        return config;
    }

    public void setConfig(ChoiceConfigEnum config) {
        this.config = config;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public ChoiceEnum getChoice() {
        return choice;
    }

    public void setChoice(ChoiceEnum choice) {
        this.choice = choice;
    }

    public List<ChoiceEnum> getChoices(){
        return config.getAvailableChoice();
    }

}
