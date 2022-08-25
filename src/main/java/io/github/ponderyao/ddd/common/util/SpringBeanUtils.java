package io.github.ponderyao.ddd.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Spring Bean 工具类
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringBeanUtils.applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        T bean = null;
        try {
            bean = (T) applicationContext.getBean(clazz);
        } catch (Exception e) {
            
        }
        if (ObjectUtils.isNull(bean)) {
            String beanName = clazz.getSimpleName();
            beanName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
            bean = (T) applicationContext.getBean(beanName);
        }
        return bean;
    }
    
    public static Map<String, Object> getBeansByAnnotation(Class<? extends Annotation> annotation) {
        return applicationContext.getBeansWithAnnotation(annotation);
    }

}