package io.github.ponderyao.ddd.io;

import io.github.ponderyao.ddd.marker.Entity;

/**
 * Converter：Entity 与 PO 的转换器
 * 
 * 默认的 Converter 需要自行实现方法中的类属性映射。当类结构
 * 复杂时映射代码将会非常多，推荐使用 MapStruct 属性映射工具
 * 实现。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface Converter<E extends Entity, PO> {
    
    PO toPO(E entity);
    
    E toEntity(PO po);
    
}
