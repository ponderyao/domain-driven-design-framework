package io.github.ponderyao.ddd.store;

import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.marker.Aggregate;
import io.github.ponderyao.ddd.marker.EntityIdentifier;
import io.github.ponderyao.ddd.marker.Repository;

/**
 * RepositorySupport：通用仓储支撑类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public abstract class RepositorySupport<T extends Aggregate<ID>, ID extends EntityIdentifier> implements Repository<T, ID> {
    
    private final Class<T> clazz;

    private final AggregateSnapshotManager<T, ID> aggregateSnapshotManager;
    
    protected RepositorySupport(Class<T> clazz) {
        this.clazz = clazz;
        this.aggregateSnapshotManager = AggregateSnapshotManager.newInstance(clazz);
    }

    public Class<T> getClazz() {
        return clazz;
    }

    protected AggregateSnapshotManager<T, ID> getAggregateSnapshotManager() {
        return aggregateSnapshotManager;
    }
    
    protected abstract T select(ID id);
    
    protected abstract void insert(T aggregate);
    
    protected abstract void update(T aggregate, AggregateDiff diff);
    
    protected abstract void delete(ID id);

    @Override
    public void attach(T aggregate) {
        this.aggregateSnapshotManager.attach(aggregate);
    }

    @Override
    public void detach(T aggregate) {
        this.aggregateSnapshotManager.detach(aggregate);
    }

    @Override
    public T find(ID id) {
        T aggregate = this.select(id);
        if (ObjectUtils.isNotNull(aggregate)) {
            this.attach(aggregate);
        }
        return aggregate;
    }

    @Override
    public void remove(T aggregate) {
        if (ObjectUtils.isNotNull(aggregate) && ObjectUtils.isNotNull(aggregate.getId())) {
            this.delete(aggregate.getId());
            this.detach(aggregate);
        }
    }

    @Override
    public void save(T aggregate) {
        if (ObjectUtils.isNotNull(aggregate.getId())) {
            this.insert(aggregate);
            this.attach(aggregate);
        } else {
            AggregateDiff aggregateDiff = aggregateSnapshotManager.detectChange(aggregate);
            if (!aggregateDiff.isEmpty()) {
                this.update(aggregate, aggregateDiff);
                aggregateSnapshotManager.merge(aggregate);
            }
        }
    }
}
