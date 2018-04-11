package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.constant.Constants;

/**
 * Created by 袁兵 on 2017/9/20.
 */
public class PageQueryBo {
    private int current;
    private int size = Constants.DEFAULT_PAGE_SIZE;

    public int getOffset() {
        return (getCurrent() - 1) * size;
    }

    public int getCurrent() {
        return current <= 0 ? 1 : current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static final PageQueryBo ALL = new PageQueryBo();

    static {
        ALL.setCurrent(1);
        ALL.setSize(Integer.MAX_VALUE);
    }
}
