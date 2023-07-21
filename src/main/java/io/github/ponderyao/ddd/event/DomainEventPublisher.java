package io.github.ponderyao.ddd.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DomainEventPublisher：领域事件唯一发布者
 *
 * @author PonderYao
 * @since 1.1.0
 */
@Component
public class DomainEventPublisher {
    
    @Autowired
    private DomainEventBus eventBus;
    
    public void publish(DomainEvent domainEvent) {
        eventBus.dispatch(domainEvent);
    }
    
}
