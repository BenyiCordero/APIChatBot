package com.chatBotStadistics.dto;

/**
 * Represents a response specific to an administrative user in the system.
 *
 * This record is used to encapsulate the minimal set of information
 * required to describe an admin user, including their name and email.
 */
public record AdminUserResponse(
        String name,
        String email
) {
}
