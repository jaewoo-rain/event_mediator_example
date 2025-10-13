package com.example.event_example.api;

import com.example.event_example.core.dto.LoginRequest;
import com.example.event_example.core.dto.RegisterRequest;
import com.example.event_example.core.event.DomainEvent;
import com.example.event_example.core.event.login.LoginRequested;
import com.example.event_example.core.event.register.RegisterRequested;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.BlockingQueue;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    private final BlockingQueue<DomainEvent> inputQueue;

    public UserController(BlockingQueue<DomainEvent> inputQueue) {
        this.inputQueue = inputQueue;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) throws InterruptedException {
        RegisterRequested event = RegisterRequested.of(req.name(), req.email(), req.password());
        inputQueue.put(event); // Initiating Event를 큐에 넣기
        log.info("[API] register 종료 id={} email={}", event.eventId(), event.email());
        return ResponseEntity.ok().body("성공 이벤트 ID:" + event.eventId());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) throws InterruptedException {
        LoginRequested event = LoginRequested.of(request.username(), request.password());
        inputQueue.put(event);
        log.info("[API] login 이벤트 id={}", event.eventId());
        return ResponseEntity.ok().body("성공 이벤트 ID:" + event.eventId());
    }
}
