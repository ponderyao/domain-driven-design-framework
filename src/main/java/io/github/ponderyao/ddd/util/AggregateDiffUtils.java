package io.github.ponderyao.ddd.util;

import io.github.ponderyao.ddd.annotation.IgnoreDiff;
import io.github.ponderyao.ddd.common.exception.ReflectionNullPropertyException;
import io.github.ponderyao.ddd.common.exception.TypeMismatchException;
import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.common.util.ReflectionUtils;
import io.github.ponderyao.ddd.marker.Aggregate;
import io.github.ponderyao.ddd.store.AggregateDiff;
import io.github.ponderyao.ddd.store.CommonDiff;
import io.github.ponderyao.ddd.store.Diff;
import io.github.ponderyao.ddd.store.strategy.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * AggregateDiffUtils：聚合差异识别工具类
 * 
 * 在目前版本的设计中，对于一个 Aggregate，其属性可能为：
 *      * Entity            实体类
 *      * DomainPrimitive   领域原语
 *      * List[Entity]      实体列表
 *      * Wrapper           包装类
 * <p>
 * 由于属性的多类型支持，采用原始 if-else 方式实现类型识别将导致代码臃肿
 * 的同时，可读性低，且可维护性与可扩展性较差，建议采用策略模式实现。
 * <p>
 * 然而，因为除包装类的原始性之外其余类型属性均具备嵌套的可能性，在求属性
 * 差异时可能需要递归处理；在该场景中，由于每种属性类型所包含的子属性类型
 * DomainPrimitive，其子属性类型只能是 DomainPrimitive 或 Wrapper，
 * 存在不同的限制，如对于 Entity，其子属性类型支持以上四种类型，而对于 
 * 这种递归模式与策略模式之间的逻辑比单纯两者结合更复杂。
 * <p>
 * 因此，框架定义了一种复杂的策略模式：<Strong>递归策略模式</Strong>
 * 递归策略模式的定义如下：
 * <p>
 * 对于每种策略类 Policy，在内部定义一个与之对应的静态 PolicyManager，
 * 维护一组可识别的策略（可能的子属性类型），在按照优先级排列后，调用该管
 * 理类即可实现递归策略的多态性。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class AggregateDiffUtils {
    
    public static AggregateDiff findDiff(Aggregate agg1, Aggregate agg2) {
        if (ObjectUtils.isNull(agg1) && ObjectUtils.isNull(agg2)) {
            return AggregateDiff.EMPTY;
        }
        if (ObjectUtils.isNotNull(agg1) && ObjectUtils.isNotNull(agg2)) {
            if (agg1.getClass() != agg2.getClass()) {
                throw new TypeMismatchException();
            }
        }
        Aggregate agg = ObjectUtils.isNotNull(agg1) ? agg1 : agg2;
        Class<?> clazz = agg.getClass();
        AggregateDiff aggregateDiff = new AggregateDiff();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (ReflectionUtils.hasAnnotation(field.getName(), clazz, IgnoreDiff.class)) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value1 = ObjectUtils.isNotNull(agg1) ? field.get(agg1) : null;
                Object value2 = ObjectUtils.isNotNull(agg2) ? field.get(agg2) : null;
                handleFieldDiff(aggregateDiff, field, value1, value2);
            } catch (IllegalAccessException e) {
                throw new ReflectionNullPropertyException();
            }
        }
        return aggregateDiff;
    }
    
    private static void handleFieldDiff(AggregateDiff aggregateDiff, Field field, Object obj1, Object obj2) {
        if (ObjectUtils.isNull(obj1) && ObjectUtils.isNull(obj2)) {
            return;
        }
        Diff fieldDiff = DiffPolicyManager.execute(obj1, obj2);
        if (ObjectUtils.isNotNull(fieldDiff)) {
            if (fieldDiff instanceof CommonDiff) {
                aggregateDiff.selfModified();
            } else {
                aggregateDiff.addDiff(field.getName(), fieldDiff);
            }
        }
    }
    
    
    private static class DiffPolicyManager {
        
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
