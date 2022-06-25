package io.github.ponderyao.ddd.io;

import io.github.ponderyao.ddd.marker.Entity;

/**
 * Assembler：DTO 与 Entity 的转换器
 * 
 * 默认的 Assembler 需要自行实现方法中的类属性映射。当类结构
 * 复杂时映射代码将会非常多，推荐使用 MapStruct 属性映射工具
 * 实现。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface Assembler<DTO, E extends Entity> {
    
    DTO toDTO(E entity);
    
    E toEntity(DTO dto);
    
}
