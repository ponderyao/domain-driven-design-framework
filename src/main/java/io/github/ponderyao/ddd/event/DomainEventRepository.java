package io.github.ponderyao.ddd.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * DomainEventRepository：领域事件仓储
 * 
 * DomainEventRepository 是领域事件机制的最底层实现，其维护一个仓储库
 * 用于存储事件及其对应的监听类。<p>
 * 
 * DomainEventBus 领域事件总线与 DomainEventRegister 领域事件注册器
 * 都依赖于 DomainEventRepository，其中，总线负责请求仓储查询，注册器
 * 负责请求仓储贮存。<p>
 * 
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings("rawtypes")
@Component
public class DomainEventRepository {
    
    private final ListMultimap<Class<? extends DomainEvent>, DomainEventHandlerWrapper> eventHandlerMapper = ArrayListMultimap.create();
    
    public void save(Class<? extends DomainEvent> eventClazz, DomainEventHandler eventHandler, int order, boolean async) {
        eventHandlerMapper.put(eventClazz, new DomainEventHandlerWrapper(eventHandler, order, async));
    }
    
    public List<DomainEventHandlerWrapper> find(Class<? extends DomainEvent> eventClazz) {
        return eventHandlerMapper.get(eventClazz);
    }
    
    public void sort() {
        Set<Class<? extends DomainEvent>> keySet = eventHandlerMapper.keySet();
        for (Class<? extends DomainEvent> key: keySet) {
            List<DomainEventHandlerWrapper> handlerList = eventHandlerMapper.get(key);
            handlerList.sort((Comparator.comparingInt(DomainEventHandlerWrapper::getOrder)));
        }
    }
    
}
