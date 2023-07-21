package io.github.ponderyao.ddd.config;

import org.springframework.aop.Advisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import io.github.ponderyao.ddd.config.command.CommandHandlingConfig;
import io.github.ponderyao.ddd.config.concurrent.ThreadPoolConfig;
import io.github.ponderyao.ddd.config.domain.DomainEventConfig;
import io.github.ponderyao.ddd.config.domain.DomainEventProperties;
import io.github.ponderyao.ddd.config.transaction.TransactionManagerConfig;
import io.github.ponderyao.ddd.config.transaction.TransactionProperties;

/**
 * DomainDrivenDesignAutoConfiguration：框架核心自动装配类
 * 
 * 基于 SpringBoot Starter & autoConfigure 思想，实现框架的基础配置
 * 自定义化，方便动态加载框架的多种特性。<p>
 * 
 * 对于非 [领域驱动设计] 范畴之内的自动装配实现，统一采用 @Import 注解
 * 导入；DomainDrivenDesignAutoConfiguration 只负责装配具备领域驱动
 * 设计特性的 Bean 实例。<p>
 *
 * @see DomainDrivenDesignProperties
 * @author PonderYao
 * @since 1.1.0
 */
@Configuration
@EnableConfigurationProperties(DomainDrivenDesignProperties.class)
@Import({ThreadPoolConfig.class, TransactionManagerConfig.class})
@ConditionalOnProperty(prefix = DomainDrivenDesignProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class DomainDrivenDesignAutoConfiguration {
    
    private final DomainDrivenDesignProperties properties;

    public DomainDrivenDesignAutoConfiguration(DomainDrivenDesignProperties properties) {
        this.properties = properties;
    }

    /**
     * 领域事件配置 Bean
     * Bean的意义在于触发 init 方法，注册领域事件拦截器
     * 
     * @param defaultThreadPoolTaskExecutor 默认线程池 Bean
     * @return domainEventConfig
     */
    @Bean(initMethod = "init")
    @ConditionalOnProperty(prefix = DomainEventProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public DomainEventConfig domainEventConfig(ThreadPoolTaskExecutor defaultThreadPoolTaskExecutor) {
        return new DomainEventConfig(properties.getDomain().getEvent(), defaultThreadPoolTaskExecutor);
    }

    /**
     * 复杂指令处理配置 Bean
     * 
     * @return commandHandlingConfig
     */
    @Bean(initMethod = "init")
    public CommandHandlingConfig commandHandlingConfig() {
        return new CommandHandlingConfig();
    }

    /**
     * 全局事务配置 Bean
     * 
     * @return transactionAdvice
     */
    @Bean
    @ConditionalOnProperty(prefix = TransactionProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public TransactionInterceptor transactionAdvice(TransactionManagerConfig transactionManagerConfig, DataSourceTransactionManager transactionManager) {
        return transactionManagerConfig.initTransactionInterceptor(transactionManager, properties.getTransaction());
    }

    /**
     * 全局事务切面 Bean
     * 
     * @param transactionAdvice 全局事务配置 Bean
     * @return transactionAdvisor
     */
    @Bean
    @ConditionalOnProperty(prefix = TransactionProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public Advisor transactionAdvisor(TransactionInterceptor transactionAdvice, TransactionManagerConfig transactionManagerConfig) {
        return transactionManagerConfig.initTransactionAdvisor(transactionAdvice, properties.getTransaction().getPointcutExpression());
    }
    
}
