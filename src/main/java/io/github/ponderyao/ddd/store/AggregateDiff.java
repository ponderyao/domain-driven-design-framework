package io.github.ponderyao.ddd.store;

import java.util.HashMap;
import java.util.Map;

/**
 * AggregateDiff：聚合差异
 * 
 * AggregateDiff 用于 Repository 模式的变更追踪机制。当
 * Aggregate 与内存快照存在差异时，具体差异记录在该实例中，
 * 且仅支持 EntityDiff 与 ListDiff 两种差异（与持久化相
 * 关）。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class AggregateDiff implements Diff {
    
    public static final AggregateDiff EMPTY = new AggregateDiff();
    
    private Map<String, Diff> diffMap = new HashMap<>();
    
    private boolean selfModified;
    
    public AggregateDiff() {
        this.selfModified = false;
    }

    public boolean isEmpty() {
        return diffMap.size() == 0;
    }
    
    private void setSelfModified(boolean selfModified) {
        this.selfModified = selfModified;
    }
    
    public void selfModified() {
        this.setSelfModified(true);
    }
    
    public boolean isSelfModified() {
        return this.selfModified;
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
        return null;
    }
}
