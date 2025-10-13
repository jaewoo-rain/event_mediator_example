package com.example.event_example.core.handler;

import com.example.event_example.core.event.login.LoginRequested;
import com.example.event_example.core.event.login.TokenTask;
import com.example.event_example.domain.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@Slf4j
public class LoginEvnetHandler implements DomainEventHandler<LoginRequested> {

    private final BlockingQueue<TokenTask> tokenCh;
    private final UserService userService;

    public LoginEvnetHandler(BlockingQueue<TokenTask> tokenCh, UserService userService) {
        this.tokenCh = tokenCh;
        this.userService = userService;
    }

    @Override
    public Class<LoginRequested> supportsType() { return LoginRequested.class; }

    @Override
    public void handle(LoginRequested event) throws Exception {
//        userService.login(event.username(), event.password());

        tokenCh.put(new TokenTask(event.username(), event.eventId()));

    }

}
