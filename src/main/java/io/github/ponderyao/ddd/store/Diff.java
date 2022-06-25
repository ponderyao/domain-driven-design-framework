package io.github.ponderyao.ddd.store;

/**
 * Diff：对象差异接口
 * 
 * 对于同一类的两个对象，存在差异时产生 Diff 实例
 *
 * @author PonderYao
 * @see DiffType
 * @since 1.0.0
 */
public interface Diff {
    
    DiffType getType();
    
}
