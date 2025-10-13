package com.example.event_example.core.dto;

public record RegisterRequest(
        String name,
        String email,
        String password
) {}
