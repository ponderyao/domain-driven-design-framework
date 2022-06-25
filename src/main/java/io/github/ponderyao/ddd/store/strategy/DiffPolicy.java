package io.github.ponderyao.ddd.store.strategy;

import io.github.ponderyao.ddd.store.Diff;

/**
 * DiffPolicy：属性差异的识别与搜索策略
 *
 * @author PonderYao
 * @since 1.0.0
 */
public abstract class DiffPolicy {

    /**
     * 策略匹配方法，根据目标属性的类型选择对应策略
     * @param obj 目标属性
     * @return true-匹配 | false-不匹配
     */
    public abstract boolean match(Object obj);

    /**
     * 策略执行方法，将两个属性进行对比，搜索其差异
     * @param obj1 目标属性1
     * @param obj2 目标属性2
     * @return 对象差异（属性差异）
     */
    public abstract Diff execute(Object obj1, Object obj2);
    
}
