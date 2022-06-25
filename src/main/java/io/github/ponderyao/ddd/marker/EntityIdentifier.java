package io.github.ponderyao.ddd.marker;

/**
 * EntityIdentifier：实体标识
 * 
 * 实体标识的 Marker Interface 标记接口。<p>
 * 
 * Entity 的 ID 标识类也是一种 DomainPrimitive。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface EntityIdentifier extends Identifier, DomainPrimitive {
    
}
