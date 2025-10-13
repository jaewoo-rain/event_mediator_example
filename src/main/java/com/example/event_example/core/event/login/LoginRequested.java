package com.example.event_example.core.event.login;

import com.example.event_example.core.event.DomainEvent;

import java.util.UUID;

public record LoginRequested(String username, String password, String eventId) implements DomainEvent {
    public static LoginRequested of(String username, String password){
        return new LoginRequested(username, password, UUID.randomUUID().toString());
    }
}
