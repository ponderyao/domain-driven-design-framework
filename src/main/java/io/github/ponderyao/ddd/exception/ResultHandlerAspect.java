package io.github.ponderyao.ddd.exception;

import io.github.ponderyao.ddd.annotation.ResultHandler;
import io.github.ponderyao.ddd.io.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * ResultHandlerAspect：接口异常拦截切面
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Aspect
@Component
public class ResultHandlerAspect {

    @Around("@annotation(io.github.ponderyao.ddd.annotation.ResultHandler)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception exception) {
            // 获取切点方法的注解
            Method method = getMethod(joinPoint);
            ResultHandler annotation = method.getAnnotation(ResultHandler.class);
            Class<? extends Throwable>[] catchExceptions = annotation.catchExceptions();
            for (Class<? extends Throwable> catchException : catchExceptions) {
                if (exception.getClass() == catchException) {
                    return Result.error(exception.getMessage());
                }
            }
            return Result.error("未声明拦截的异常:" + exception.getMessage());
        }
        return proceed;
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
    
}
