package com.zifangdt.ch.market.web;

import com.github.pagehelper.Page;

import java.util.List;

public class PageDto<T> {
    private List<T> list;
    private int page;
    private int size;
    private long total;

    public static <T> PageDto<T> fromPage(Page<T> page){
       PageDto<T> pageDto = new PageDto<>();
       pageDto.setTotal(page.getTotal());
       pageDto.setSize(page.getPageSize());
       pageDto.setPage(page.getPageNum());
       pageDto.setList(page.getResult());
       return pageDto;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
