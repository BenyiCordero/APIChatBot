package com.chatBotStadistics.controllers;

import com.chatBotStadistics.dto.AuthRequest;
import com.chatBotStadistics.dto.TokenResponse;
import com.chatBotStadistics.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the AuthController class.
 *
 * This test class is responsible for testing the authentication functionality
 * provided by AuthController using MockMvc and a mocked AuthService.
 * It verifies the behavior of the authentication endpoint under various scenarios
 * such as valid requests, invalid requests, and handling exceptions.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    /**
     * Tests the authenticate method in AuthController.
     */
    @Test
    void givenValidAuthRequest_whenAuthenticate_thenReturnTokenResponse() throws Exception {
        // Arrange
        AuthRequest validRequest = new AuthRequest("test@example.com", "password123");
        TokenResponse tokenResponse = new TokenResponse("access-token-123", "refresh-token-456");

        Mockito.when(authService.authenticate(validRequest)).thenReturn(tokenResponse);

        // Act & Assert
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "email": "test@example.com",
                                            "password": "password123"
                                        }
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("access-token-123"))
                .andExpect(jsonPath("$.refresh_token").value("refresh-token-456"));
    }

    @Test
    void givenInvalidAuthRequest_whenAuthenticate_thenReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "email": "",
                                            "password": ""
                                        }
                                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAuthServiceThrowsException_whenAuthenticate_thenReturnInternalServerError() throws Exception {
        // Arrange
        AuthRequest invalidRequest = new AuthRequest("invalid@example.com", "wrongpassword");

        Mockito.when(authService.authenticate(invalidRequest)).thenThrow(RuntimeException.class);

        // Act & Assert
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "email": "invalid@example.com",
                                            "password": "wrongpassword"
                                        }
                                        """))
                .andExpect(status().isInternalServerError());
    }
}