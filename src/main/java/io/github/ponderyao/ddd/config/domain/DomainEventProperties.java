package io.github.ponderyao.ddd.config.domain;

/**
 * DomainEventProperties：自定义领域事件装配属性
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class DomainEventProperties {
    
    public static final String PREFIX = DomainProperties.PREFIX + "event";
    
    private Boolean enabled = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
