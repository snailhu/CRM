package com.zifangdt.ch.base.bo.cfg.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ApprovedBy;
import com.zifangdt.ch.base.enums.ConfigType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by 袁兵 on 2018/1/17.
 */
@JsonIgnoreProperties(value = "choiceList", allowGetters = true)
public class DetailOfFlowStep extends BaseConfigDetail {

    private String name;
    private ApprovedBy approvedBy;
    private Set<Long> choices;
    private String description;
    private String uuid;
    private String prev;
    private String next;

    private Object choiceList;

    public Object getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(Object choiceList) {
        this.choiceList = choiceList;
    }

    public DetailOfFlowStep() {
    }

    public DetailOfFlowStep(String name, ApprovedBy approvedBy, String uuid) {
        this.name = name;
        this.approvedBy = approvedBy;
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApprovedBy getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(ApprovedBy approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Set<Long> getChoices() {
        return choices;
    }

    public void setChoices(Set<Long> choices) {
        this.choices = choices;
    }

    @Override
    public List<ConfigItem> parse(ConfigType configType, List<String[]> lines) {
        DetailOfFlowStep last = null;
        List<ConfigItem> items = new ArrayList<>();
        for (String[] line : lines) {
            DetailOfFlowStep step = new DetailOfFlowStep(line[0], ApprovedBy.valueOf(line[1]), UUID.randomUUID().toString());
            if (line.length == 3) {
                step.setDescription(line[2]);
            }
            if (last != null) {
                last.setNext(step.getUuid());
                step.setPrev(last.getUuid());
            }
            last = step;
            items.add(new ConfigItem(configType, step));
        }
        return items;
    }
}
