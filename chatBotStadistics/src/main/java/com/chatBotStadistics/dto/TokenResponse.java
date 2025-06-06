package com.chatBotStadistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a response containing authentication tokens.
 *
 * This record encapsulates the access token and the refresh token
 * provided as part of an authentication or token refresh workflow.
 *
 * The access token is typically used for authorizing requests,
 * while the refresh token is used to obtain a new access token
 * when the current one expires.
 */
public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
