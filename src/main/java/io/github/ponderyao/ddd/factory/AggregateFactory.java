package io.github.ponderyao.ddd.factory;

import io.github.ponderyao.ddd.marker.Aggregate;

/**
 * AggregateFactory：聚合工厂
 * 
 * 通过不同的预定义的 Prototype 来构造 Aggregate，可以重载多次
 * create 方法。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface AggregateFactory<T extends Aggregate> {
    
    T create(Prototype prototype);
    
}
