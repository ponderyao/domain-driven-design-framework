package io.github.ponderyao.ddd.config;

import io.github.ponderyao.ddd.config.domain.DomainProperties;
import io.github.ponderyao.ddd.config.transaction.TransactionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * DomainDrivenDesignProperties：框架自定义装配属性
 *
 * @author PonderYao
 * @since 1.1.0
 */
@ConfigurationProperties(prefix = DomainDrivenDesignProperties.PREFIX)
public class DomainDrivenDesignProperties {
    
    public static final String PREFIX = "ponder.ddd";
    
    @NestedConfigurationProperty
    private DomainProperties domain;
    
    @NestedConfigurationProperty
    private TransactionProperties transaction;

    public DomainProperties getDomain() {
        return domain;
    }

    public void setDomain(DomainProperties domain) {
        this.domain = domain;
    }

    public TransactionProperties getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionProperties transaction) {
        this.transaction = transaction;
    }
}
