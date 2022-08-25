package io.github.ponderyao.ddd.config.concurrent;

import io.github.ponderyao.ddd.config.DomainDrivenDesignProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * PoolProperties：自定义池化装配属性
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class PoolProperties {
    
    public static final String PREFIX = DomainDrivenDesignProperties.PREFIX + "pool";
    
    @NestedConfigurationProperty
    private ThreadPoolProperties thread = new ThreadPoolProperties();

    public ThreadPoolProperties getThread() {
        return thread;
    }

    public void setThread(ThreadPoolProperties thread) {
        this.thread = thread;
    }
}
