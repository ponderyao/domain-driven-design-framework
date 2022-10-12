package io.github.ponderyao.ddd.config.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolConfig：线程池配置类
 *
 * @author PonderYao
 * @since 1.1.0
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfig {

    Logger log = LoggerFactory.getLogger(ThreadPoolConfig.class);
    
    private final ThreadPoolProperties properties;

    public ThreadPoolConfig(ThreadPoolProperties properties) {
        this.properties = properties;
    }

    /**
     * 默认线程池 Bean
     * 
     * @return defaultThreadPoolTaskExecutor
     */
    @Bean(name = "defaultThreadPoolTaskExecutor")
    @ConditionalOnProperty(prefix = ThreadPoolProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public ThreadPoolTaskExecutor defaultThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        try {
            executor.setRejectedExecutionHandler(properties.getRejectedExecutionHandler().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn("The rejected-execution-handler configuration of thread-pool-task-executor could not be recognized, error:" + e.getMessage());
            log.info("Road the default rejected-execution-handler: CallerRunsPolicy");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        }
        log.info("Build the default thread-pool-task-executor finish");
        return executor;
    }
    
}
