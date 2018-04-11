package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.enums.OrderBy;
import com.zifangdt.ch.base.enums.Sort;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public class SortablePageQueryBo extends PageQueryBo {
    private OrderBy orderBy;
    private Sort sort = Sort.desc;

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
