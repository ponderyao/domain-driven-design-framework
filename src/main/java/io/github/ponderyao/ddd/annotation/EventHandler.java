package io.github.ponderyao.ddd.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * EventHandler：自定义注解：事件拦截（监听）
 * 
 * @author PonderYao
 * @since 1.1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface EventHandler {
    
    int order() default 0;
    
    boolean async() default false;
    
}
