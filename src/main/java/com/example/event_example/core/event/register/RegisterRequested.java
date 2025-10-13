package com.example.event_example.core.event.register;

import com.example.event_example.core.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

/**
 * 회원가입 요청이 큐에 들어갈 때 사용
 */
public record RegisterRequested(
        String eventId, String name, String email, String password, Instant at
) implements DomainEvent {
    public static RegisterRequested of(String name, String email, String password) {
        return new RegisterRequested(UUID.randomUUID().toString(), name, email, password, Instant.now());
    }
}
