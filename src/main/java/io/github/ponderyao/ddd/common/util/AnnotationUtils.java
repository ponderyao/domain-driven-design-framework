package io.github.ponderyao.ddd.common.util;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * AnnotationUtils：注解工具类
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {
    
    public static Object getAnnotationAttribute(Class<?> targetClazz, Class<? extends Annotation> annoClazz, String field) {
        Annotation annotation = findAnnotation(targetClazz, annoClazz);
        if (ObjectUtils.isNotNull(annotation)) {
            Map<String, Object> annotationAttributes = getAnnotationAttributes(annotation);
            return annotationAttributes.get(field);
        }
        return null;
    }
    
}
