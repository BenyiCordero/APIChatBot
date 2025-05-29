package com.chatBotStadistics.dto;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
