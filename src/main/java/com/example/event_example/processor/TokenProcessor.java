package com.example.event_example.processor;

import com.example.event_example.core.event.login.TokenTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@Slf4j @Component
public class TokenProcessor {

    public TokenProcessor(BlockingQueue<TokenTask> tokenChannel,
                          ExecutorService pool) {
        pool.submit(() -> {
            log.info("[TokenProcessor] 대기");
            while (true) {
                TokenTask task = tokenChannel.take();
                log.info("[TokenProcessor] 완료 유저ID= {}, 이벤트 ID= {}",task.username(), task.verificationCode());
            }
        });
    }
}
