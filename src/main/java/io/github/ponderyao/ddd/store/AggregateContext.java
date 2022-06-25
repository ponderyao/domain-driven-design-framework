package io.github.ponderyao.ddd.store;

import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.common.util.ReflectionUtils;
import io.github.ponderyao.ddd.marker.Aggregate;
import io.github.ponderyao.ddd.marker.EntityIdentifier;
import io.github.ponderyao.ddd.util.AggregateDiffUtils;
import io.github.ponderyao.ddd.util.SnapshotUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * AggregateContext：聚合上下文
 * 
 * AggregateContext 维护一个聚合哈希表，通过聚合根（ID）
 * 来访问获取聚合在内存中保存的快照。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class AggregateContext<T extends Aggregate<ID>, ID extends EntityIdentifier> {
    
    private Class<? extends T> clazz;
    
    private Map<ID, T> aggregateMap = new HashMap<>();
    
    public AggregateContext(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends T> getClazz() {
        return clazz;
    }
    
    public void attach(T aggregate) {
        if (ObjectUtils.isNotNull(aggregate.getId())) {
            if (!aggregateMap.containsKey(aggregate.getId())) {
                this.merge(aggregate);
            }
        }
    }
    
    public void merge(T aggregate) {
        if (ObjectUtils.isNotNull(aggregate.getId())) {
            T snapshot = SnapshotUtils.snapshot(aggregate);
            this.aggregateMap.put(aggregate.getId(), snapshot);
        }
    }
    
    public void detach(T aggregate) {
        if (ObjectUtils.isNotNull(aggregate.getId())) {
            this.aggregateMap.remove(aggregate.getId());
        }
    }
    
    public T find(ID id) {
        return aggregateMap.get(id);
    }
    
    public AggregateDiff detectChange(T aggregate) {
        if (ObjectUtils.isNull(aggregate.getId())) {
            return AggregateDiff.EMPTY;
        }
        T snapshot = this.aggregateMap.get(aggregate.getId());
        if (ObjectUtils.isNull(snapshot)) {
            this.attach(aggregate);
        }
        return AggregateDiffUtils.findDiff(snapshot, aggregate);
    }
    
    public void setId(T aggregate, ID id) {
        ReflectionUtils.setPropertyValueByName("id", aggregate, id);
    }
    
}
