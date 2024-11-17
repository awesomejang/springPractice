package com.springpractice.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncExecutorConfig {

    @Bean(name = "mainAsyncExecutor")
    public ThreadPoolTaskExecutor mainAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 쓰레드 풀의 기본(최소) 쓰레드 수, 코어 풀의 쓰레드가 모두 사용중일경우 추가 요청은 대기 큐에 저장
        executor.setMaxPoolSize(10); // 쓰레드 풀에서 생성할 수 있는 최대 쓰레드 수, 요청이 많아질 경우 최대 쓰레드 수 까지 생성 가능
        executor.setQueueCapacity(0); // 코어 풀 쓰레드가 모두 바쁠 경우, 대기 요청을 저장할 큐의 크기 -> 이 큐 사이즈까지는 기본 쓰레드 수 이상으로 쓰레드를 생성하지 않는다.(그 이후에 쓰레드 max pool 까지 생성)
        executor.setThreadNamePrefix("mainAsyncExecutor-");
        executor.initialize();
        return executor;
    }
}
