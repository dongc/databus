package com.znlhzl.databus.task.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author dongchuang
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * corePoolSize
     */
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * maxPoolSize
     */
    private static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * BlockingQueue Capacity
     */
    private static final int QUEUE_CAPACITY = 128;
    /**
     * 元数据加工:后续处理
     */
    private static final String META_SUBSEQUENT = "Meta_Sub";

    @Bean
    public Executor metalAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(META_SUBSEQUENT);
        /*CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行*/
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
