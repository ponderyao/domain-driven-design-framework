package io.github.ponderyao.ddd.store.strategy;

import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.marker.Entity;
import io.github.ponderyao.ddd.marker.Identifier;
import io.github.ponderyao.ddd.store.Diff;
import io.github.ponderyao.ddd.store.EntityDiff;
import io.github.ponderyao.ddd.store.ListDiff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ListDiffPolicy：实体列表属性差异的识别与搜索策略
 * 
 * 对于类型为 List[Entity] 的子属性，其差异搜索策略定义在 
 * ListDiffPolicyManager 内部类中
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ListDiffPolicy extends DiffPolicy {
    
    @Override
    public boolean match(Object obj) {
        return obj instanceof List<?>;
    }

    @Override
    public Diff execute(Object obj1, Object obj2) {
        if (ObjectUtils.isNull(obj1) && ObjectUtils.isNull(obj2)) {
            return null;
        }
        ListDiff listDiff = null;
        List<?> list1 = (List<?>) obj1;
        List<?> list2 = (List<?>) obj2;
        Map<Identifier, Entity> entityMap = new HashMap<>();
        for (Object listItem : list1) {
            if (listItem instanceof Entity) {
                Entity entity = (Entity) listItem;
                entityMap.put(entity.getId(), entity);
            }
        }
        for (Object listItem : list2) {
            if (listItem instanceof  Entity) {
                Entity entity = (Entity) listItem;
                Diff fieldDiff = ListDiffPolicyManager.execute(entityMap.get(entity.getId()), entity);
                if (ObjectUtils.isNotNull(fieldDiff) && fieldDiff instanceof EntityDiff) {
                    if (ObjectUtils.isNull(listDiff)) {
                        listDiff = new ListDiff();
                    }
                    listDiff.add((EntityDiff) fieldDiff);
                }
                entityMap.remove(entity.getId());
            }
        }
        for (Entity entity : entityMap.values()) {
            Diff fieldDiff = ListDiffPolicyManager.execute(entity, null);
            if (ObjectUtils.isNotNull(fieldDiff) && fieldDiff instanceof EntityDiff) {
                if (ObjectUtils.isNull(listDiff)) {
                    listDiff = new ListDiff();
                }
                listDiff.add((EntityDiff) fieldDiff);
            }
        }
        return listDiff;
    }


    private static class ListDiffPolicyManager {

        private static final List<DiffPolicy> POLICIES = new ArrayList<>();

        static {
            POLICIES.add(new EntityDiffPolicy());
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
