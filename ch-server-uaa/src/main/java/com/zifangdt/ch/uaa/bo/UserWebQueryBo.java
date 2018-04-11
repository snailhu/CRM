package com.zifangdt.ch.uaa.bo;

import com.zifangdt.ch.base.bo.PageQueryBo;
import com.zifangdt.ch.base.validation.IntegerEnumValue;

/**
 * Created by 袁兵 on 2017/10/17.
 */
public class UserWebQueryBo extends PageQueryBo {
    private String search;
    @IntegerEnumValue({0, 1})
    private Integer status;

    public String getSearchPattern() {
        return String.format("%%%s%%", search);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
