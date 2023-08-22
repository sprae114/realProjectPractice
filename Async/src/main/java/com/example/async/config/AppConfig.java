package com.example.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfig {

    /**
     * 기본적인 작업에 사용할 쓰레드 풀
     */
    @Bean(name = "defaultTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor defaultTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // 쓰레드 풀 생성해, 멀티 쓰레드로 동작하게 해줌
        executor.setCorePoolSize(200); // 쓰레드 풀 기본 사이즈
        executor.setMaxPoolSize(200); // 쓰레드 풀 최대 사이즈
        return executor;
    }

    /**
     *  메시징 작업과 관련된 처리에 사용할 쓰레드 풀
     */
    @Bean(name = "messagingTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor messagingTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(200);
        return executor;
    }
}