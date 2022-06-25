package io.github.ponderyao.ddd.marker;

/**
 * Validatable：可校验合法性
 * 
 * 常在实体的构造方法中引用该接口规定的 isValid 方法。<p>
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface Validatable {
    
    boolean isValid();
    
}
