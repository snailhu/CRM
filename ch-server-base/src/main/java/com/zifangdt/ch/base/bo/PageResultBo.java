package com.zifangdt.ch.base.bo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToLongFunction;

/**
 * Created by 袁兵 on 2017/9/27.
 */
public class PageResultBo<U> {
    /**
     * 当前第几页
     */
    private int current;
    /**
     * 每页的记录数
     */
    private int size;
    /**
     * 总记录数
     */
    private long totalRecords;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 记录结果集
     */
    private List<U> records;

    private Boolean permittedToImport;
    private Boolean permittedToExport;

    private Object extraInfo;

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Boolean getPermittedToImport() {
        return permittedToImport;
    }

    public void setPermittedToImport(Boolean permittedToImport) {
        this.permittedToImport = permittedToImport;
    }

    public Boolean getPermittedToExport() {
        return permittedToExport;
    }

    public void setPermittedToExport(Boolean permittedToExport) {
        this.permittedToExport = permittedToExport;
    }

    public static <T extends PageQueryBo, U> PageResultBo<U> explicitPage(T pageQueryBo, Function<T, List<U>> recordsFunction, ToLongFunction<T> countFunction, Consumer<U> consumer) {
        PageResultBo<U> pageResultBo = explicitPage(pageQueryBo, recordsFunction, countFunction);
        pageResultBo.getRecords().forEach(consumer);
        return pageResultBo;
    }

    public static <T extends PageQueryBo, U> PageResultBo<U> explicitPage(T pageQueryBo, Function<T, List<U>> recordsFunction, ToLongFunction<T> countFunction) {
        long total = countFunction.applyAsLong(pageQueryBo);
        PageResultBo<U> pageResultBo = new PageResultBo<>();
        pageResultBo.setCurrent(pageQueryBo.getCurrent());
        pageResultBo.setSize(pageQueryBo.getSize());
        pageResultBo.setTotalRecords(total);
        pageResultBo.setTotalPages((int) (total / pageQueryBo.getSize() + ((total % pageQueryBo.getSize() == 0) ? 0 : 1)));
        pageResultBo.setRecords(recordsFunction.apply(pageQueryBo));
        return pageResultBo;
    }

    public static <T extends PageQueryBo, U> PageResultBo<U> of(T pageQueryBo, Function<T, List<U>> function) {
        PageHelper.startPage(pageQueryBo.getCurrent(), pageQueryBo.getSize());
        PageInfo<U> pageInfo = new PageInfo<>(function.apply(pageQueryBo));
        PageResultBo<U> pageResultBo = new PageResultBo<>();
        pageResultBo.setCurrent(pageInfo.getPageNum());
        pageResultBo.setSize(pageInfo.getPageSize());
        pageResultBo.setTotalRecords(pageInfo.getTotal());
        pageResultBo.setTotalPages(pageInfo.getPages());
        pageResultBo.setRecords(pageInfo.getList());
        return pageResultBo;
    }

    public static <T extends PageQueryBo, U> PageResultBo<U> of(T pageQueryBo, Function<T, List<U>> function, Consumer<U> consumer) {
        PageResultBo<U> pageResultBo = of(pageQueryBo, function);
        pageResultBo.getRecords().forEach(consumer);
        return pageResultBo;
    }

    public int getCurrent() {
        return current;
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

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<U> getRecords() {
        return records;
    }

    public void setRecords(List<U> records) {
        this.records = records;
    }
}
