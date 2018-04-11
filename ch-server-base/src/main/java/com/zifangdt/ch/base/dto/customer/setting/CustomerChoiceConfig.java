package com.zifangdt.ch.base.dto.customer.setting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.BaseChoiceConfig;

import javax.persistence.Table;

/**
 * 客户设置，多选1
 */
@Table(name = "cfg_choice_config")
@JsonIgnoreProperties(value={ "choices" }, allowGetters=true)
public class CustomerChoiceConfig extends BaseChoiceConfig<Long> {

}