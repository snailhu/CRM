package com.zifangdt.ch.base.dto.product.output;

import com.zifangdt.ch.base.dto.FromEntity;
import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import com.zifangdt.ch.base.enums.product.RepoType;
import org.springframework.beans.BeanUtils;

/**
 * 入库单简单信息，用于列表返回
 */
public class InStockChangeBrief extends InStockChange implements FromEntity<InStockChange, InStockChangeBrief>{
    private String repoName;
    private RepoType repoType;
    @Override
    public InStockChangeBrief convertFrom(InStockChange entity) {
        BeanUtils.copyProperties(entity, this);
        return this;
    }

    public String getRepoName() {
        return repoName;
    }

    public InStockChangeBrief setRepoName(String repoName) {
        this.repoName = repoName;
        return this;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public void setRepoType(RepoType repoType) {
        this.repoType = repoType;
    }
}
