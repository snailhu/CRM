package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.enums.pair.CustomerSourceRelationType;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public class IdAndNameOfSource extends IdAndName {
    private List<IdAndName> relations;
    private boolean hasRelations;
    private CustomerSourceRelationType type;

    public CustomerSourceRelationType getType() {
        return type;
    }

    public void setType(CustomerSourceRelationType type) {
        this.type = type;
    }

    public boolean isHasRelations() {
        return hasRelations;
    }

    public void setHasRelations(boolean hasRelations) {
        this.hasRelations = hasRelations;
    }

    public List<IdAndName> getRelations() {
        return relations;
    }

    public void setRelations(List<IdAndName> relations) {
        this.relations = relations;
    }
}
