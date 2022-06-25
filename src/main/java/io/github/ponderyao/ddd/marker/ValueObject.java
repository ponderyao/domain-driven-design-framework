package io.github.ponderyao.ddd.marker;

/**
 * ValueObject：值对象
 * 
 * 值对象的 Marker Interface 标记接口。<p>
 * 
 * ValueObject 是一种设计模式。ValueObject 规定对象是没有
 * 具体状态标识的数据结构，可视为多个基础类型数据的简单封装，
 * 复杂的值对象甚至可以支持值对象嵌套。ValueObject 是一种信
 * 息最直观的载体，与业务有关，但并非 Entity 一般具有唯一标
 * 识的有状态实体。<p>
 * 
 * ValueObject 具备的基础特性是：属性相等则对象相等。可通过
 * 重写类的 <code>equals</code> 与 <code>hashCode</code>
 * 方法来实现。<p>
 * 
 * 此外，ValueObject 还具备 immutability 不可篡改的特性，
 * 修改值对象必须以替换值对象的手段实现。<p>
 * 
 * 尽管构造一个 ValueObject 的推荐实践是基于 Factory 工厂
 * 模式来实现，但复杂的领域驱动设计中，ValueObject 的多样性
 * 会导致 Factory 模式实践上的臃肿；相对的，如果 Aggregate
 * 使用 Factory 模式会更加适合。因此，在此建议，依然使用最
 * 简单的类构造方法来创建 ValueObject。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface ValueObject {
    
    boolean equals(Object obj);
    
    int hashCode();
    
}
