package io.github.ponderyao.ddd.exception;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.github.ponderyao.ddd.annotation.ResultHandler;
import io.github.ponderyao.ddd.common.exception.DomainPrimitiveValidationException;
import io.github.ponderyao.ddd.common.exception.EntityValidationException;
import io.github.ponderyao.ddd.common.exception.ReflectionNullPropertyException;
import io.github.ponderyao.ddd.common.exception.TypeMismatchException;
import io.github.ponderyao.ddd.io.Result;

/**
 * ResultHandlerAspect：接口异常拦截切面
 * 
 * 可设置多切面来拦截 @ResultHandler 注解，并通过 @Order 控制
 * 切面执行优先级。框架提供最低优先级的切面处理。
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Aspect
@Component
@Order(1)
public class ResultHandlerAspect {
    
    private static final List<Class<? extends Throwable>> defaultCatchExceptions = new ArrayList<>();
    
    static {
        defaultCatchExceptions.add(DomainPrimitiveValidationException.class);
        defaultCatchExceptions.add(EntityValidationException.class);
        defaultCatchExceptions.add(ReflectionNullPropertyException.class);
        defaultCatchExceptions.add(TypeMismatchException.class);
    }

    @Around("@annotation(io.github.ponderyao.ddd.annotation.ResultHandler)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception exception) {
            // 获取切点方法的注解
            Method method = getMethod(joinPoint);
            ResultHandler annotation = method.getAnnotation(ResultHandler.class);
            Class<? extends Throwable>[] catchExceptions = annotation.catchExceptions();
            catchExceptions = addDefaultCatchExceptions(catchExceptions);
            for (Class<? extends Throwable> catchException : catchExceptions) {
                if (exception.getClass() == catchException) {
                    return Result.fail(transferCode(catchException.getSimpleName()), exception.getMessage());
                }
            }
            // 未事先声明的异常
            return Result.fail("UNDECLARED_ERROR", exception.getMessage());
        }
        return result;
    }

    /**
     * 根据切点获取切点方法
     * @param joinPoint 切点
     * @return 切点方法
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }
    
    private Class<? extends Throwable>[] addDefaultCatchExceptions(Class<? extends Throwable>[] exceptions) {
        List<Class<? extends Throwable>> exceptionList = new ArrayList<>(exceptions.length + 5);
        exceptionList.addAll(defaultCatchExceptions);
        Collections.addAll(exceptionList, exceptions);
        return exceptionList.toArray(exceptions);
    }

    private static String transferCode(String str) {
        Pattern compile = Pattern.compile("[A-Z]");
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb,  "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase();
    }
    
}
