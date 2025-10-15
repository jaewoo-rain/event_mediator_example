package com.example.event_example.core.mediator;

import com.example.event_example.core.event.DomainEvent;
import com.example.event_example.core.handler.DomainEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Component @Slf4j
public class RegistryBasedMediator implements EventMediator {

    private final BlockingQueue<DomainEvent> input;
    private final Map<Class<?>, List<DomainEventHandler<?>>> handlers = new ConcurrentHashMap<>();
    private final UnhandledEventStrategy unhandled; // (아래 참고)

    public RegistryBasedMediator(BlockingQueue<DomainEvent> input,
                                 List<DomainEventHandler<?>> discoveredHandlers,
                                 Optional<UnhandledEventStrategy> unhandled
    ) {
        this.input = input;
        // 우선순위별 정렬하여 타입별로 레지스터
        discoveredHandlers.forEach(h -> handlers
                .computeIfAbsent(h.supportsType(), k -> new ArrayList<>())
                .add(h));
        handlers.values().forEach(list -> list.sort(Comparator.comparingInt(domainEventHandler -> domainEventHandler.order())));
        this.unhandled = unhandled.orElseGet(() -> (e) -> { throw new IllegalStateException("No handler for "+e.getClass()); });
        log.info("[Mediator] registered handlers: {}", handlers.keySet());
    }

    @Override
    public void run() {
        log.info("[Mediator] started");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var event = input.take();
                handle(event);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Throwable t) {
                log.error("[Mediator] unexpected error", t);
            }
        }
    }

    @Override
    @SuppressWarnings({"rawtypes","unchecked"})
    public void handle(DomainEvent event) {
        var list = handlers.get(event.getClass());
        if (list == null || list.isEmpty()) {
            unhandled.onUnhandled(event);
            return;
        }
        for (DomainEventHandler h : list) {
            try {
                h.handle(event);
            } catch (Exception ex) {
                // 개별 핸들러 예외 처리 정책: 중단/계속/재시도 등
                log.error("[Mediator] handler error type={} handler={} msg={}",
                        event.getClass().getSimpleName(), h.getClass().getSimpleName(), ex.getMessage(), ex);
                // 기본은 다음 핸들러 계속 (필요 시 정책 주입)
            }
        }
    }

    /** 핸들러 미존재 시 전략 (DLQ 전송 등으로 대체 가능) */
    public interface UnhandledEventStrategy {
        void onUnhandled(DomainEvent e);
    }
}