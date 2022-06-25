package io.github.ponderyao.ddd.factory;

import io.github.ponderyao.ddd.marker.Entity;

/**
 * EntityFactory：实体工厂
 * 
 * 通过不同的预定义的 Prototype 来构造 Entity，可以重载多次
 * create 方法。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface EntityFactory<T extends Entity> {
    
    T create(Prototype prototype);
    
}
