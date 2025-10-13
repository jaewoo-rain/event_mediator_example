package com.example.event_example.boot;

import com.example.event_example.core.mediator.EventMediator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MediatorRunner implements CommandLineRunner {
    private final EventMediator mediator;

    public MediatorRunner(EventMediator mediator) { this.mediator = mediator; }

    @Override
    public void run(String... args) {
        Thread t = new Thread(mediator, "registration-mediator"); // 혹은 @Async로 대체
        t.setDaemon(true);
        t.start();
    }
}