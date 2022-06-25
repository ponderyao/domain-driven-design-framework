package io.github.ponderyao.ddd.common.util;

import io.github.ponderyao.ddd.common.exception.ReflectionNullPropertyException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ReflectionUtils：反射工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ReflectionUtils {
    
    public static <T> Map<String, Object> getPropertiesMap(T obj) {
        Map<String, Object> propertiesMap = new HashMap<>();
        if (obj != null) {
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String propertyName = field.getName();
                Object propertyValue = ReflectionUtils.getPropertyValueByName(propertyName, obj);
                propertiesMap.put(propertyName, propertyValue);
            }
        }
        return propertiesMap;
    }
    
    public static Object getPropertyValueByName(String propertyName, Object obj) {
        try {
            String firstLetter = propertyName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + propertyName.substring(1);
            Method method = obj.getClass().getMethod(getter);
            return method.invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static boolean hasAnnotation(String propertyName, Class<?> clazz, Class<? extends Annotation> annotation) {
        try {
            return clazz.getDeclaredField(propertyName).isAnnotationPresent(annotation);
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
    
    public static <T> void setPropertyValueByName(String propertyName, T obj, Object property) {
        Class<?> clazz = obj.getClass();
        try {
            String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            Method method = clazz.getMethod(methodName, property.getClass());
            method.invoke(obj, property);
        } catch (NoSuchMethodException e) {
            throw new ReflectionNullPropertyException();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
}
