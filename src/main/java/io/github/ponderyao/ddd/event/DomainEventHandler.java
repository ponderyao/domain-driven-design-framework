package io.github.ponderyao.ddd.event;

import io.github.ponderyao.ddd.io.Result;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * DomainEventHandler：领域事件监听类接口
 * 
 * 通过实现 DomainEventHandler 接口，在完成事件绑定，当监听到
 * 对应事件产生时，执行重载的 execute 方法。<p>
 * 
 * DomainEventHandler 提供了 getExecutor 默认空方法，当实现
 * 类选择重载该方法时，可以配置指定的线程池；否则，将按系统默认
 * 的线程池来完成同一事件的多监听者分发执行。<p>
 * 
 * @author PonderYao
 * @since 1.1.0
 */
public interface DomainEventHandler<E extends DomainEvent, R extends Result> {

    /**
     * 指定线程池，默认不指定，使用系统默认
     * @return ThreadPoolTaskExecutor
     */
    default ThreadPoolTaskExecutor getExecutor() {
        return null;
    }
    
    R execute(E event);
    
}
