package com.chatBotStadistics.dto;

/**
 * Represents an authentication request consisting of user credentials.
 *
 * This record is used to encapsulate the necessary information required
 * for user authentication, including email and password.
 *
 * It is typically used as part of an authentication workflow where
 * the provided credentials are verified against stored user data.
 */
public record AuthRequest(
        String email,
        String password
) {
}
