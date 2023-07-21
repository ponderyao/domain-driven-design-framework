package io.github.ponderyao.ddd.event;

/**
 * DomainEventHandlerWrapper：领域事件拦截器的封装类
 * 
 * 原生的 DomainEventHandler 领域事件拦截器仅为接口，用于提供接口
 * 方法。通过对接口封装形成 DomainEventHandlerWrapper，能够指定
 * order 优先级属性，以及 async 是否异步属性。<p>
 *
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings("rawtypes")
public class DomainEventHandlerWrapper {
    
    private DomainEventHandler eventHandler;
    
    private Integer order;
    
    private Boolean async;
    
    public DomainEventHandlerWrapper(DomainEventHandler eventHandler) {
        new DomainEventHandlerWrapper(eventHandler, 0, false);
    }

    public DomainEventHandlerWrapper(DomainEventHandler eventHandler, int order, boolean async) {
        this.eventHandler = eventHandler;
        this.order = order;
        this.async = async;
    }

    public DomainEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(DomainEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean isAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }
}
