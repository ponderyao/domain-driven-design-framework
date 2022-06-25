package io.github.ponderyao.ddd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ResultHandler：自定义注解：接口响应异常拦截处理
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultHandler {
    
    Class<? extends Throwable>[] catchExceptions() default {};
    
}
