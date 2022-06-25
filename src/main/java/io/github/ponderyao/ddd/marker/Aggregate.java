package io.github.ponderyao.ddd.marker;

/**
 * Aggregate：业务聚合
 * 
 * 业务聚合的 Marker Interface 标记接口。<p>
 * 
 * Aggregate 是特殊的 Entity，特点在于 Aggregate 可以包含
 * 多个 Entity，并且对于被包含的 Entity，必须受到 Aggregate 
 * 的约束与限制。Aggregate 的存在避免了业务指令单独操控某个 
 * Entity 的持久化条件，对于同一个 Aggregate 内的所有 Entity，
 * 其状态与 Aggregate 完全绑定，只有通过对整个 Aggregate 的
 * 操作，才能影响 Entity 的变化。<p>
 * 
 * 在简单的业务建模中，如果不存在复杂的业务实体映射，即对于只
 * 影响单个实体的业务，应当将 Aggregate 视为普通的 Entity，
 * 而非直接以 Entity 取代 Aggregate 来作为业务载体。原因是 
 * Repository 仓储模式的存在，其规定必须绑定 Aggregate，即
 * Aggregate 与 Repository 是一一对应的，不建议重构成基于 
 * Entity 的 Repository。<p>
 *
 * @author PonderYao
 * @see Identifier
 * @see Entity
 * @since 1.0.0
 */
public interface Aggregate<ID extends EntityIdentifier> extends Entity<ID> {
    
}
