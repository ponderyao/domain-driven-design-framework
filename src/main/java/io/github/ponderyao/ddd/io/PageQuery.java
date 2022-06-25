package io.github.ponderyao.ddd.io;

/**
 * PageQuery：分页查询型输入对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class PageQuery extends Query {
    
    private static final long serialVersionUID = 6601537906583509118L;
    
    private int pageSize;
    
    private int pageIndex;
    
    private boolean needTotalCount;
    
    public PageQuery(int pageSize, int pageIndex, boolean needTotalCount) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.needTotalCount = needTotalCount;
    }
    
    public int getPageSize() {
        return this.pageSize;
    }
    
    public int getPageIndex() {
        return this.pageIndex;
    }
    
    public boolean isNeedTotalCount() {
        return this.needTotalCount;
    }
    
}
