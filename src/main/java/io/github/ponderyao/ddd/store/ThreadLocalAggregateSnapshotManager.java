package io.github.ponderyao.ddd.store;

import io.github.ponderyao.ddd.marker.Aggregate;
import io.github.ponderyao.ddd.marker.EntityIdentifier;

/**
 * ThreadLocalAggregateSnapshotManager：聚合快照管理器的 ThreadLocal 实现
 * 
 * 避免多线程下 Aggregate 的不安全复用。每个线程单独维护一个
 * AggregateContext 聚合上下文，保存聚合的快照信息。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ThreadLocalAggregateSnapshotManager<T extends Aggregate<ID, ?>, ID extends EntityIdentifier> implements AggregateSnapshotManager<T, ID> {

    private final Class<? extends T> clazz;
    
    private final ThreadLocal<AggregateContext<T, ID>> context;
    
    public ThreadLocalAggregateSnapshotManager(Class<? extends T> clazz) {
        this.clazz = clazz;
        this.context = ThreadLocal.withInitial(() -> new AggregateContext<>(this.clazz));
    }
    
    @Override
    public void attach(T aggregate) {
        this.context.get().attach(aggregate);
    }
    
    @Override
    public void attach(T aggregate, ID id) {
        this.context.get().setId(aggregate, id);
        this.attach(aggregate);
    }

    @Override
    public void detach(T aggregate) {
        this.context.get().detach(aggregate);
    }

    @Override
    public T find(ID id) {
        return this.context.get().find(id);
    }

    @Override
    public void merge(T aggregate) {
        this.context.get().merge(aggregate);
    }

    @Override
    public AggregateDiff detectChange(T aggregate) {
        return this.context.get().detectChange(aggregate);
    }

}
