package io.github.ponderyao.ddd.event;

import io.github.ponderyao.ddd.annotation.EventHandler;
import io.github.ponderyao.ddd.common.util.AnnotationUtils;
import io.github.ponderyao.ddd.common.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * DomainEventRegister：领域事件注册器
 * 
 * 对于 EventHandler 注解，其内部参数 order & async 的优先级
 * 低于额外注解 {@link org.springframework.core.annotation.Order}
 * & {@link org.springframework.scheduling.annotation.Async}，
 * 在使用过程中要注意目标配置是否被覆盖。
 * 
 * @author PonderYao
 * @see DomainEvent
 * @see DomainEventHandler
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
        Type genericSuperclass = eventHandler.getClass().getGenericInterfaces()[0];
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericSuperclass;
            return (Class<? extends DomainEvent>) type.getActualTypeArguments()[0];
        }
        return null;
    }
    
    private int getOrderPriority(DomainEventHandler eventHandler) {
        int order = 0;
        Object orderField = AnnotationUtils.getAnnotationAttribute(eventHandler.getClass(), Order.class, "value");
        if (ObjectUtils.isNotNull(orderField)) {
            order = (int) orderField;
            return order;
        }
        orderField = AnnotationUtils.getAnnotationAttribute(eventHandler.getClass(), EventHandler.class, "order");
        if (ObjectUtils.isNotNull(orderField)) {
            order = (int) orderField;
        }
        return order;
    }
    
    private boolean getAsync(DomainEventHandler eventHandler) {
        boolean async = ObjectUtils.isNotNull(AnnotationUtils.findAnnotation(eventHandler.getClass(), Async.class));
        if (!async) {
            Object asyncField = AnnotationUtils.getAnnotationAttribute(eventHandler.getClass(), EventHandler.class, "async");
            if (ObjectUtils.isNotNull(asyncField)) {
                async = (boolean) asyncField;
            }
        }
        return async;
    }
    
    public void afterRegister() {
        eventRepository.sort();
    }
    
}
