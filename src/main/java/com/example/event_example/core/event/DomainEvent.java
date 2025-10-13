package com.example.event_example.core.event;

public interface DomainEvent {
    default String eventId() { return null; }
}
