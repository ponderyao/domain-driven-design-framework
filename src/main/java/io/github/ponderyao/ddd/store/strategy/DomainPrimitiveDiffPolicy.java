package io.github.ponderyao.ddd.store.strategy;

import io.github.ponderyao.ddd.annotation.IgnoreDiff;
import io.github.ponderyao.ddd.common.exception.ReflectionNullPropertyException;
import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.common.util.ReflectionUtils;
import io.github.ponderyao.ddd.marker.DomainPrimitive;
import io.github.ponderyao.ddd.store.CommonDiff;
import io.github.ponderyao.ddd.store.Diff;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * DomainPrimitiveDiffPolicy：领域原语属性差异的识别与执行策略
 * 
 * 对于类型为 DomainPrimitive 的子属性，其差异搜索策略定义在 
 * DomainPrimitiveDiffPolicyManager 内部类中
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DomainPrimitiveDiffPolicy extends DiffPolicy {
    
    @Override
    public boolean match(Object obj) {
        return obj instanceof DomainPrimitive;
    }

    @Override
    public Diff execute(Object obj1, Object obj2) {
        if (ObjectUtils.isNull(obj1) && ObjectUtils.isNull(obj2)) {
            return null;
        }
        if (ObjectUtils.isNull(obj1) || ObjectUtils.isNull(obj2)) {
            return new CommonDiff();
        }
        // 尽管框架建议 DomainPrimitive 重写 equals 方法，但为了避免无该实现导致此处逻辑错误，仍通过反射识别属性差异
        // 其次，反射亦能实现对嵌套属性的 @IgnoreDiff 注解进行识别
        Class<?> clazz = obj1.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (ReflectionUtils.hasAnnotation(field.getName(), clazz, IgnoreDiff.class)) {
                continue;
            }
            try {
                field.setAccessible(true);
                Diff fieldDiff = DomainPrimitiveDiffPolicyManager.execute(field.get(obj1), field.get(obj2));
                if (ObjectUtils.isNotNull(fieldDiff)) {
                    // 某个属性存在差异，直接返回 CommonDiff
                    return new CommonDiff();
                }
            } catch (IllegalAccessException e) {
                throw new ReflectionNullPropertyException();
            }
        }
        return null;
    }


    private static class DomainPrimitiveDiffPolicyManager {

        private static final List<DiffPolicy> POLICIES = new ArrayList<>();

        static {
            POLICIES.add(new DomainPrimitiveDiffPolicy());
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
