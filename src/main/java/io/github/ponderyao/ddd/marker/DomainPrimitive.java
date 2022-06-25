package io.github.ponderyao.ddd.marker;

/**
 * DomainPrimitive：领域原语（无状态值对象）
 * 
 * 无状态值对象的 Marker Interface 标记接口。<p>
 * 
 * DomainPrimitive 是业务领域的最小载体与原生语言，其本质
 * 是一个 ValueObject。DomainPrimitive 支持嵌套定义，一个
 * DomainPrimitive 代表一个具体的静态业务数据。<p>
 * 
 * 与 DomainPrimitive 相反的业务对象是 Entity，Entity 是
 * 有状态的，即具有唯一标识的；DomainPrimitive 是无状态的，
 * 不存在业务生命周期，不直接与持久化对象关联。<p>
 * 
 * DomainPrimitive 与 ValueObject 的区别在于，后者是一种
 * 设计模式与基础概念，而前者具备一定的业务特性，即存在充血
 * 模型所允许的业务行为。DomainPrimitive 还具备 Validity
 * 特性，允许对象规定业务上的合法性校验方法。<p>
 *
 * @author PonderYao
 * @see ValueObject
 * @see Validatable
 * @since 1.0.0
 */
public interface DomainPrimitive extends ValueObject, Validatable {

}
