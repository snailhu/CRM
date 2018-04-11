package com.zifangdt.ch.base.bo;

/**
 * Created by 袁兵 on 2018/1/25.
 */
public class NewsPageQueryBo extends PageQueryBo {

    private Long relatedId;

    public NewsPageQueryBo(){
        super.setSize(20);
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }
}
