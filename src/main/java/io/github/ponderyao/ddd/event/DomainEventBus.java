package io.github.ponderyao.ddd.event;

import io.github.ponderyao.ddd.common.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DomainEventBus：领域事件总线
 * 
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class DomainEventBus {

    @Autowired
    private DomainEventRepository eventRepository;
    
    private ThreadPoolTaskExecutor defaultExecutor = new ThreadPoolTaskExecutor();
    
    public void setDefaultExecutor(ThreadPoolTaskExecutor defaultExecutor) {
        this.defaultExecutor = defaultExecutor;
    }
    
    public void dispatch(DomainEvent domainEvent) {
        List<DomainEventHandlerWrapper> eventHandlerWrappers = eventRepository.find(domainEvent.getClass());
        eventHandlerWrappers.forEach(wrapper -> {
            DomainEventHandler eventHandler = wrapper.getEventHandler();
            if (wrapper.isAsync()) {
                ThreadPoolTaskExecutor bindExecutor = eventHandler.getExecutor();
                if (ObjectUtils.isNull(bindExecutor)) {
                    bindExecutor = defaultExecutor;
                }
                bindExecutor.submit(() -> eventHandler.execute(domainEvent));
            } else {
                eventHandler.execute(domainEvent);
            }
        });
    }
    
}
