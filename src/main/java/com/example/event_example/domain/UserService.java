package com.example.event_example.domain;

import com.example.event_example.core.event.register.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service @Slf4j
public class UserService {
    public User register(String name, String email, String password) {
        User user = new User(UUID.randomUUID().toString(), name, email, password);
        log.info("[UserService] 유저: 아이디= {}, 이메일= {}, 비밀번호= {}", user.id(), user.email(), user.password());
        return user;
    }
}
