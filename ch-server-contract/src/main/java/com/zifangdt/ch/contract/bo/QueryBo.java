package com.zifangdt.ch.contract.bo;

import com.zifangdt.ch.base.bo.SortablePageQueryBo;
import com.zifangdt.ch.base.enums.OrderBy;
import com.zifangdt.ch.base.validation.IntegerEnumValue;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/22.
 */
public class QueryBo extends SortablePageQueryBo {
    private String search;
    @IntegerEnumValue({0, 1, 2})
    private int ownerType;//0-全部合同，1-我的合同，2-我下属的合同
    private List<Long> owners;

    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Long> getOwners() {
        return owners;
    }

    public void setOwners(List<Long> owners) {
        this.owners = owners;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    @Override
    public OrderBy getOrderBy() {
        return super.getOrderBy() == null ? OrderBy.createTime : super.getOrderBy();
    }
}
