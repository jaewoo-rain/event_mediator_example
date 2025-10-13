package com.example.event_example.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class NotificationService {
    public void sendWelcome(String email) {
        log.info("[NotificationService] 이메일= {}", email);
    }
}
