package com.chatBotStadistics.controllers;

import com.chatBotStadistics.dto.AuthRequest;
import com.chatBotStadistics.dto.RegisterRequest;
import com.chatBotStadistics.dto.TokenResponse;
import com.chatBotStadistics.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication-related operations.
 *
 * This class contains endpoints to allow users to register, log in, and refresh authentication tokens.
 * The controller is annotated with {@code @RestController} to indicate that it is a RESTful web service
 * and maps requests to the base URI "/auth" using {@code @RequestMapping}.
 *
 * Dependencies are injected into this controller using the {@code @RequiredArgsConstructor} annotation.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        final TokenResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest request) {
        final TokenResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication
    ) {
        return service.refreshToken(authentication);
    }

}
