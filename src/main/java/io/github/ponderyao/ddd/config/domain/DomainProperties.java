package io.github.ponderyao.ddd.config.domain;

import io.github.ponderyao.ddd.config.DomainDrivenDesignProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * DomainProperties：自定义领域装配属性
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class DomainProperties {
    
    public static final String PREFIX = DomainDrivenDesignProperties.PREFIX + ".domain";
    
    @NestedConfigurationProperty
    private DomainEventProperties event = new DomainEventProperties();

    public DomainEventProperties getEvent() {
        return event;
    }

    public void setEvent(DomainEventProperties event) {
        this.event = event;
    }
}
