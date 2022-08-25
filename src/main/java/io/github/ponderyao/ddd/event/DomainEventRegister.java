package io.github.ponderyao.ddd.event;

import io.github.ponderyao.ddd.common.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * DomainEventRegister：领域事件注册器
 * 
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class DomainEventRegister {
    
    @Autowired
    private DomainEventRepository eventRepository;
    
    public void register(DomainEventHandler eventHandler) {
        Class<? extends DomainEvent> eventClazz = getDomainEventFromHandler(eventHandler);
        if (ObjectUtils.isNotNull(eventClazz)) {
            int order = getOrderPriority(eventHandler);
            boolean async = getAsync(eventHandler);
            eventRepository.save(eventClazz, eventHandler, order, async);
        }
    }
    
    private Class<? extends DomainEvent> getDomainEventFromHandler(DomainEventHandler eventHandler) {
        Type genericSuperclass = eventHandler.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericSuperclass;
            return (Class<? extends DomainEvent>) type.getActualTypeArguments()[0];
        }
        return null;
    }
    
    private int getOrderPriority(DomainEventHandler eventHandler) {
        int order = 0;
        Order annotation = AnnotationUtils.findAnnotation(eventHandler.getClass(), Order.class);
        if (ObjectUtils.isNotNull(annotation)) {
            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            order = (int) annotationAttributes.get("value");
        }
        return order;
    }
    
    private boolean getAsync(DomainEventHandler eventHandler) {
        return ObjectUtils.isNotNull(AnnotationUtils.findAnnotation(eventHandler.getClass(), Async.class));
    }
    
    public void afterRegister() {
        eventRepository.sort();
    }
    
}
