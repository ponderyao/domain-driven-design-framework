package io.github.ponderyao.ddd.store;

import io.github.ponderyao.ddd.marker.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * EntityDiff：实体差异
 * 
 * EntityDiff 不单独出现，依赖于 AggregateDiff 或 ListDiff。
 * EntityDiff 代表两个 Entity 之间存在内容差异，前提是 Entity
 * 已重写 <code>equals</code> 与 <code>hashCode</code> 方
 * 法。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class EntityDiff implements Diff {
    
    private Entity oldValue;
    
    private Entity newValue;
    
    private DiffType type;
    
    private Map<String, Diff> diffMap = new HashMap<>();
    
    public EntityDiff(Entity oldValue, Entity newValue, DiffType type) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.type = type;
    }

    public void addDiff(String propertyName, Diff diff) {
        diffMap.put(propertyName, diff);
    }

    public Diff getDiff(String propertyName) {
        if (diffMap.containsKey(propertyName)) {
            return diffMap.get(propertyName);
        }
        return null;
    }
    
    @Override
    public DiffType getType() {
        return this.type;
    }

    public Entity getOldValue() {
        return this.oldValue;
    }

    public Entity getNewValue() {
        return this.newValue;
    }
    
}
