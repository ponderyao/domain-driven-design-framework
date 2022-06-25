package io.github.ponderyao.ddd.io;

import io.github.ponderyao.ddd.common.constant.ResponseStatus;
import io.github.ponderyao.ddd.common.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * PageResult：分页结果型输出对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class PageResult<T> extends DTO {
    
    private static final long serialVersionUID = 3184492332794308622L;
    
    public static final String DEFAULT_MSG = "操作成功";
    
    public static final int DEFAULT_PAGE_SIZE = 1;
    
    public static final int DEFAULT_PAGE_INDEX = 1;

    private int code;

    private String msg;

    private Collection<T> data;
    
    private int totalCount;
    
    private int pageIndex;
    
    private int pageSize;
    
    public PageResult(int code, String msg, Collection<T> data, int totalCount, int pageIndex, int pageSize) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.totalCount = totalCount;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public List<T> getData() {
        return ObjectUtils.isNull(this.data) ? Collections.emptyList() : new ArrayList<>(this.data);
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getTotalPages() {
        return this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : (this.totalCount / this.pageSize) + 1;
    }
    
    public boolean isEmpty() {
        return ObjectUtils.isNull(this.data) || this.data.isEmpty();
    }
    
    public static <T> PageResult<T> success(Collection<T> data) {
        int totalCount = data.size();
        if (totalCount == 0) {
            return PageResult.notFound();
        }
        return PageResult.success(data, totalCount, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_INDEX);
    }
    
    public static <T> PageResult<T> success(Collection<T> data, int totalCount, int pageSize, int pageIndex) {
        if (data.size() == 0) {
            return PageResult.notFound(totalCount, pageSize, pageIndex);
        }
        return new PageResult<>(ResponseStatus.SUCCESS, DEFAULT_MSG, data, totalCount, pageSize, pageIndex);
    }
    
    public static <T> PageResult<T> notFound() {
        return PageResult.notFound(0, 0, 0);
    }
    
    public static <T> PageResult<T> notFound(int totalCount, int pageSize, int pageIndex) {
        return new PageResult<>(ResponseStatus.NOT_FOUND, "数据不存在", null, totalCount, pageSize, pageIndex);
    }

    public static <T> PageResult<T> error(String msg) {
        return PageResult.error(msg, 0, 0, 0);
    }
    
    public static <T> PageResult<T> error(String msg, int totalCount, int pageSize, int pageIndex) {
        return new PageResult<>(ResponseStatus.ERROR, msg, null, totalCount, pageIndex, pageIndex);
    }
}
