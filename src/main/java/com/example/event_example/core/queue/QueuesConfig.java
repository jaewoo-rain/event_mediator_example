package com.example.event_example.core.queue;

import com.example.event_example.core.event.DomainEvent;
import com.example.event_example.core.event.login.TokenTask;
import com.example.event_example.core.event.register.EmailTask;
import com.example.event_example.core.event.register.NotificationTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class QueuesConfig {

    /**
     * 컨트롤러 -> Mediator
     * */
    @Bean
    public BlockingQueue<DomainEvent> inputQueue() {
        return new LinkedBlockingQueue<>();
    }

    /**
     * Mediator → 프로세서
     * */
    @Bean public BlockingQueue<EmailTask> emailChannel() {
        return new LinkedBlockingQueue<>();
    }
    @Bean public BlockingQueue<NotificationTask> notificationChannel() {
        return new LinkedBlockingQueue<>();
    }
    @Bean public BlockingQueue<TokenTask> tokenChannel() {
        return new LinkedBlockingQueue<>();
    }

    /**
     * 간단 워커 쓰레드 풀 (각 프로세서가 사용)
     * */
    @Bean
    public ExecutorService workerPool() {
        return Executors.newCachedThreadPool();
    }
}
