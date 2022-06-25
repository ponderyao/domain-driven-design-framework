package io.github.ponderyao.ddd.store.strategy;

import io.github.ponderyao.ddd.annotation.IgnoreDiff;
import io.github.ponderyao.ddd.common.exception.ReflectionNullPropertyException;
import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.common.util.ReflectionUtils;
import io.github.ponderyao.ddd.marker.Entity;
import io.github.ponderyao.ddd.store.CommonDiff;
import io.github.ponderyao.ddd.store.Diff;
import io.github.ponderyao.ddd.store.DiffType;
import io.github.ponderyao.ddd.store.EntityDiff;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * EntityDiffPolicy：实体属性差异的识别与搜索策略
 * 
 * 对于类型为 Entity 的子属性，其差异搜索策略定义在 
 * EntityDiffPolicyManager 内部类中
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class EntityDiffPolicy extends DiffPolicy {
    
    @Override
    public boolean match(Object obj) {
        return obj instanceof Entity;
    }

    @Override
    public Diff execute(Object obj1, Object obj2) {
        if (ObjectUtils.isNull(obj1) && ObjectUtils.isNull(obj2)) {
            return null;
        }
        EntityDiff diff = null;
        if (ObjectUtils.isNull(obj1)) {
            diff = new EntityDiff(null, (Entity) obj2, DiffType.Added);
        }
        if (ObjectUtils.isNull(obj2)) {
            diff = new EntityDiff((Entity) obj1, null, DiffType.Removed);
        }
        Object obj = ObjectUtils.isNotNull(obj1) ? obj1 : obj2;
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (ReflectionUtils.hasAnnotation(field.getName(), clazz, IgnoreDiff.class)) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value1 = ObjectUtils.isNotNull(obj1) ? field.get(obj1) : null;
                Object value2 = ObjectUtils.isNotNull(obj2) ? field.get(obj2) : null;
                Diff fieldDiff = EntityDiffPolicyManager.execute(value1, value2);
                if (ObjectUtils.isNotNull(fieldDiff)) {
                    if (ObjectUtils.isNull(diff)) {
                        diff = new EntityDiff((Entity) obj1, (Entity) obj2, DiffType.Modified);
                    }
                    if (!(fieldDiff instanceof CommonDiff)) {
                        diff.addDiff(field.getName(), fieldDiff);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new ReflectionNullPropertyException();
            }
        }
        return diff;
    }
    

    private static class EntityDiffPolicyManager {

        private static final List<DiffPolicy> POLICIES = new ArrayList<>();
        
        static {
            POLICIES.add(new DomainPrimitiveDiffPolicy());
            POLICIES.add(new EntityDiffPolicy());
            POLICIES.add(new ListDiffPolicy());
            POLICIES.add(new CommonDiffPolicy());
        }

        public static Diff execute(Object obj1, Object obj2) {
            Object obj = ObjectUtils.isNotNull(obj1) ? obj1 : obj2;
            for (DiffPolicy policy : POLICIES) {
                if (policy.match(obj)) {
                    return policy.execute(obj1, obj2);
                }
            }
            return null;
        }

    }
    
}
