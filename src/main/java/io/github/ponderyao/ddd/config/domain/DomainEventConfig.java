package io.github.ponderyao.ddd.config.domain;

import io.github.ponderyao.ddd.annotation.EventHandler;
import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.common.util.SpringBeanUtils;
import io.github.ponderyao.ddd.event.DomainEventBus;
import io.github.ponderyao.ddd.event.DomainEventHandler;
import io.github.ponderyao.ddd.event.DomainEventRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

/**
 * DomainEventConfig：领域事件配置类
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class DomainEventConfig {

    Logger log = LoggerFactory.getLogger(DomainEventConfig.class);
    
    private final DomainEventRegister eventRegister;
    
    private final DomainEventBus eventBus;
    
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
    public DomainEventConfig(DomainEventProperties properties, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.eventRegister = SpringBeanUtils.getBean(DomainEventRegister.class);
        this.eventBus = SpringBeanUtils.getBean(DomainEventBus.class);
    }
    
    public void init() {
        initEventBus(threadPoolTaskExecutor);
        registerEventHandler();
    }
    
    protected void registerEventHandler() {
        log.info("Init domain event configuration - register domain event handlers");
        Map<String, Object> eventHandlers = SpringBeanUtils.getBeansByAnnotation(EventHandler.class);
        eventHandlers.values().forEach(handler -> {
            if (handler instanceof DomainEventHandler) {
                eventRegister.register((DomainEventHandler) handler);
            }
        });
        eventRegister.afterRegister();
    }
    
    protected void initEventBus(ThreadPoolTaskExecutor defaultExecutor) {
        if (ObjectUtils.isNotNull(defaultExecutor)) {
            eventBus.setDefaultExecutor(defaultExecutor);
        }
    }
    
}
