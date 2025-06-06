package com.chatBotStadistics.dto;

/**
 * Represents a registration request containing user details.
 *
 * This record encapsulates the necessary information required
 * for user registration, including the full name, email, and password.
 *
 * It is used as part of a registration workflow where these details
 * are processed to create a new user account in the system.
 */
public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
