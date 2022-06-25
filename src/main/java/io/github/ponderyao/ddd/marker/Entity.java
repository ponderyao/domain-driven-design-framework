package io.github.ponderyao.ddd.marker;

import java.io.Serializable;

/**
 * Entity：业务实体
 * 
 * 业务实体的 Marker Interface 标记接口。<p>
 * 
 * Entity 需存在以 Identifier 接口为标记的自定义 ID 类标识，
 * 同时实现了 Identifiable 接口以表明自身的有状态性质。<p>
 * 
 * Entity 是一种充血模型结构，每个 Entity 均可设定唯一的业务
 * 性方法，避免在被调用的业务类中重复实现或另类实现。<p>
 * 
 * 使用聚合时，为实现 Repository 变更追踪机制，Entity 必须重
 * 写 <code>equals</code> 与 <code>hashCode</code> 方法，
 * 避免出现 Entity 与快照永远不对等的情况。
 *
 * @author PonderYao
 * @see EntityIdentifier
 * @see Identifiable
 * @since 1.0.0
 */
public interface Entity<ID extends EntityIdentifier> extends Identifiable<ID>, Serializable {
    
}
