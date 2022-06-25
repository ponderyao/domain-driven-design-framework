package io.github.ponderyao.ddd.store.strategy;

import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.store.CommonDiff;
import io.github.ponderyao.ddd.store.Diff;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * CommonDiffPolicy：普通属性差异的识别与搜索策略
 * 
 * 在 Java Bean 中，普通属性指 Bean 的包装类型属性。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class CommonDiffPolicy extends DiffPolicy {
    
    public static final List<Class<?>> WRAPPER = Arrays.asList(
            Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
            Boolean.class, Character.class, String.class, Date.class);
    
    @Override
    public boolean match(Object obj) {
        return WRAPPER.contains(obj.getClass());
    }

    @Override
    public Diff execute(Object obj1, Object obj2) {
        if (ObjectUtils.isNull(obj1) && ObjectUtils.isNull(obj2)) {
            return null;
        }
        if (ObjectUtils.isNull(obj1) || ObjectUtils.isNull(obj2)) {
            return new CommonDiff();
        }
        return obj1.equals(obj2) ? null : new CommonDiff();
    }
    
}
