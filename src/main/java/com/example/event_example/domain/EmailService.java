package com.example.event_example.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service @Slf4j
public class EmailService {
    public String sendVerification(String email) {
        String code = UUID.randomUUID().toString().substring(0,8);
        log.info("[EmailService] 이메일= {} 코드= {}", email, code);
        return code; // 실패 시 예외/false 패턴도 가능
    }
}
