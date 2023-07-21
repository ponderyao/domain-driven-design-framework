package io.github.ponderyao.ddd.marker;

/**
 * Repository：仓储服务
 * 
 * 数据仓储服务的 Marker Interface 标记接口。<p>
 * 
 * Repository 的目的是将业务层与数据持久化层之间解耦，避免业务
 * 层对某一种 ORM 数据存储方式的强依赖性。Repository 通常将接
 * 口定义在 domain 层，在 infrastructure 层具体实现。<p>
 * 
 * Repository 并非特定与某一数据对象 Data Object 绑定，而是与
 * 业务聚合 Aggregate 对应。一个 Repository 可以引进多个数据
 * 访问对象 DAO，建议通过事务控制维护数据完整性。<p>
 * 
 * 仓储服务的方法名应以中性原则、无偏向持久化导向的规范来命名，
 * 例如 find/save/remove；数据持久化服务则严格按照 CRUD 模式，
 * 如 insert/select/update/delete。其中，find 与 select 对
 * 应，remove 与 delete 对应，save 则同时与 insert/update 
 * 对应。因此，对 save 的实现应考虑区分插入与修改的场景。<p>
 *
 * @author PonderYao
 * @see Aggregate
 * @see Identifier
 * @since 1.0.0
 */
public interface Repository<T extends Aggregate<ID, ?>, ID extends EntityIdentifier> {

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
     * @return Aggregate
     */
    T find(ID id);

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
