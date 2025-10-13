package com.example.event_example.core.handler;

import com.example.event_example.core.event.DomainEvent;

public interface DomainEventHandler<T extends DomainEvent> {
    /** 자신이 처리할 수 있는 이벤트 타입(정확한 Class) */
    Class<T> supportsType();

    /** 실제 이벤트 처리 (비즈니스 오케스트레이션 or 서브태스크 분배) */
    void handle(T event) throws Exception;

    /** (선택) 우선순위: 숫자 낮을수록 먼저 (같은 타입에 복수 핸들러 둘 수도) */
    default int order() { return 0; }
}