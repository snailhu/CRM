package com.zifangdt.ch.base.dto.product.output;

import com.zifangdt.ch.base.dto.FromEntity;
import com.zifangdt.ch.base.dto.product.entity.OutStockChange;
import com.zifangdt.ch.base.enums.product.RepoType;
import org.springframework.beans.BeanUtils;

/**
 * 入库单简单信息，用于列表返回
 */
public class OutStockChangeBrief extends OutStockChange implements FromEntity<OutStockChange, OutStockChangeBrief>{
    private String repoName;
    private RepoType repoType;

    @Override
    public OutStockChangeBrief convertFrom(OutStockChange entity) {
        BeanUtils.copyProperties(entity, this);
        return this;
    }

    public String getRepoName() {
        return repoName;
    }

    public OutStockChangeBrief setRepoName(String repoName) {
        this.repoName = repoName;
        return this;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public OutStockChangeBrief setRepoType(RepoType repoType) {
        this.repoType = repoType;
        return this;
    }
}
