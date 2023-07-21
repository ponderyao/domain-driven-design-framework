package io.github.ponderyao.ddd.store;

import io.github.ponderyao.ddd.marker.Aggregate;
import io.github.ponderyao.ddd.marker.EntityIdentifier;

/**
 * AggregateSnapshotManager：聚合快照管理器接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface AggregateSnapshotManager<T extends Aggregate<ID, ?>, ID extends EntityIdentifier> {

    static <T extends Aggregate<ID, ?>, ID extends EntityIdentifier> AggregateSnapshotManager<T, ID> newInstance(Class<T> clazz) {
        return new ThreadLocalAggregateSnapshotManager<>(clazz);
    }
    
    void attach(T aggregate);

    void attach(T aggregate, ID id);
    
    void detach(T aggregate);
    
    T find(ID id);
    
    void merge(T aggregate);
    
    AggregateDiff detectChange(T aggregate);
    
}
