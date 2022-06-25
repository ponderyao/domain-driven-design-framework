package io.github.ponderyao.ddd.marker;

import java.io.Serializable;

/**
 * Identifier：可序列化 ID
 * 
 * 业务实体的标识的 Marker Interface 标记接口。<p>
 * 
 * 使用自定义 ID 类，取代传统的基于基础数据类型的业务实体标识字段。
 * 自定义 ID 类应严格实现 Identifier 接口，或者继承实现了 Identifier 
 * 接口的自定义基础 ID 类。<p>
 * 
 * 自定义 ID 类的目的是实现各自领域内实现 ID 的规范校验，避免在多
 * 个业务类中出现重复的业务代码。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface Identifier extends Serializable {
}
