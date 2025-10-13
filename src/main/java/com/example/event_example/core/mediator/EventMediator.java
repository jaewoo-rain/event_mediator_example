package com.example.event_example.core.mediator;

import com.example.event_example.core.event.DomainEvent;
import com.example.event_example.core.event.register.RegisterRequested;

public interface EventMediator extends Runnable {
    @Override void run();
    void handle(DomainEvent event);
}
