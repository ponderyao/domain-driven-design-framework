package io.github.ponderyao.ddd.store;

/**
 * DiffType：对象差异类型枚举类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public enum DiffType {
    
    // 新增：旧对象为空，新对象不为空
    Added,
    
    // 修改，旧对象与新对象均不为空，且两者存在差异
    Modified,
    
    // 删除：旧对象不为空，新对象为空
    Removed
    
}
