package com.example.event_example.processor;

import com.example.event_example.core.event.register.NotificationTask;
import com.example.event_example.domain.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@Component @Slf4j
public class NotificationProcessor {

    public NotificationProcessor(BlockingQueue<NotificationTask> notificationChannel,
                                 NotificationService notificationService,
                                 ExecutorService pool) {
        pool.submit(() -> {
            log.info("[NotificationProcessor] 대기");
            while (true) {
                NotificationTask task = notificationChannel.take();
                notificationService.sendWelcome(task.email());
                log.info("[NotificationProcessor] 완료 유저= {} 이메일= {}", task.userId(), task.email());
            }
        });
    }
}
