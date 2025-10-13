package com.example.event_example.processor;


import com.example.event_example.core.event.register.EmailTask;
import com.example.event_example.domain.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@Component @Slf4j
public class EmailProcessor {

    public EmailProcessor(BlockingQueue<EmailTask> emailChannel,
                          ExecutorService pool) {
        pool.submit(() -> {
            log.info("[EmailProcessor] 대기");
            while (true) {
                EmailTask task = emailChannel.take();
                log.info("[EmailProcessor] 완료 유저ID= {} 이메일= {} 코드= {}",
                        task.userId(), task.email(), task.verificationCode());
            }
        });
    }
}
