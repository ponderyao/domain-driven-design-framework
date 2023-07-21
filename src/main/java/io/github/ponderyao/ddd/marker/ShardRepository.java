package io.github.ponderyao.ddd.marker;

/**
 * ShardRepository：分片仓储服务
 *
 * 数据仓储服务的 Marker Interface 标记接口。<p>
 * 
 * ShardRepository 原理与 Repository 一致，唯一区别在于使用
 * 场景上，Repository 不支持分库分表策略，即访问仓储时不包含对
 * 分片键的使用与处理；ShardRepository 增加了泛型参数分片键，
 * 使用方允许自定义分片键并纳入仓储管理逻辑中。<p>
 *
 * @author PonderYao
 * @since 1.1.0
 */
public interface ShardRepository<T extends Aggregate<ID, SID>, ID extends EntityIdentifier, SID extends EntityIdentifier> {

    /**
     * 将一个 Aggregate 附属到当前 Repository，使其可追踪
     * @param aggregate 聚合
     */
    void attach(T aggregate);

    /**
     * 解除当前 Repository 对目标 Aggregate 的追踪
     * @param aggregate 聚合
     */
    void detach(T aggregate);

    /**
     * 根据 ID 找到 Aggregate
     * @param id ID
     * @param shardId 分片ID
     * @return Aggregate
     */
    T find(ID id, SID shardId);

    /**
     * 删除目标 Aggregate
     * @param aggregate 聚合
     */
    void remove(T aggregate);

    /**
     * 保存目标 Aggregate
     * 统一新增与修改的数据库操作，并做存在性查询，防止修改操作出现 ID 不存在的情况
     * @param aggregate 聚合
     */
    void save(T aggregate);
    
}
