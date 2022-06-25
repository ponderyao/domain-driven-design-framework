package io.github.ponderyao.ddd.marker;

/**
 * Identifiable：可标识状态
 * 
 * 通过判断是否实现 Identifiable 接口，区分业务实体 Entity
 * /业务聚合 Aggregate 与值对象 Value Object。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface Identifiable<ID extends Identifier> {
    
    ID getId();
    
}
