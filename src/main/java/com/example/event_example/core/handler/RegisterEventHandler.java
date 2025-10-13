package com.example.event_example.core.handler;

import com.example.event_example.core.event.register.*;
import com.example.event_example.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component @Slf4j
public class RegisterEventHandler implements DomainEventHandler<RegisterRequested> {

    private final UserService userService;
    private final EmailService emailService;
    private final BlockingQueue<EmailTask> emailCh;
    private final BlockingQueue<NotificationTask> notiCh;

    public RegisterEventHandler(UserService userService,
                                EmailService emailService,
                                BlockingQueue<EmailTask> emailCh,
                                BlockingQueue<NotificationTask> notiCh) {
        this.userService = userService;
        this.emailService = emailService;
        this.emailCh = emailCh;
        this.notiCh = notiCh;
    }

    @Override public Class<RegisterRequested> supportsType() { return RegisterRequested.class; }

    @Override
    public void handle(RegisterRequested e) throws Exception {
        // 1) 동기 단계
        var user = userService.register(e.name(), e.email(), e.password());
        var verification = emailService.sendVerification(user.email());

        // 2) 채널로 비동기 분배
        emailCh.put(new EmailTask(user.id(), user.email(), verification));
        notiCh.put(new NotificationTask(user.id(), user.email()));

        log.info("[RegisterRequestedHandler] done eventId={} userId={}", e.eventId(), user.id());
    }
}
