package io.github.ponderyao.ddd.config.concurrent;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolProperties：自定义线程池装配属性
 *
 * @author PonderYao
 * @since 1.1.0
 */
@ConfigurationProperties(prefix = ThreadPoolProperties.PREFIX)
public class ThreadPoolProperties {
    
    public static final String PREFIX = PoolProperties.PREFIX + ".thread";
    
    private Boolean enabled = true;

    /** 核心线程数 */
    private Integer corePoolSize = 50;

    /** 最大线程数 */
    private Integer maxPoolSize = 200;

    /** 工作队列容量 */
    private Integer queueCapacity = 1000;

    /** 线程池维护线程所允许的空闲时间 */
    private Integer keepAliveSeconds = 300;

    /** 拒绝策略 */
    private Class<? extends RejectedExecutionHandler> rejectedExecutionHandler = ThreadPoolExecutor.CallerRunsPolicy.class;

    public Boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public Class<? extends RejectedExecutionHandler> getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    public void setRejectedExecutionHandler(Class<? extends RejectedExecutionHandler> rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }
}
