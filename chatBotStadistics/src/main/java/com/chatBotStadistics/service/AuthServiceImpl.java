package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.domain.Token;
import com.chatBotStadistics.dto.AuthRequest;
import com.chatBotStadistics.dto.RegisterRequest;
import com.chatBotStadistics.dto.TokenResponse;
import com.chatBotStadistics.repository.TokenRepository;
import com.chatBotStadistics.repository.AdminUserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Implementation of the AuthService interface for managing user authentication
 * and token-related operations in the system.
 *
 * This class provides functionalities for:
 * - Registering a new admin user with username, email, and password.
 * - Authenticating existing users and generating access and refresh tokens upon successful login.
 * - Saving authentication tokens to the database.
 * - Revoking all active tokens associated with a specific user.
 * - Refreshing authentication tokens by validating and replacing expired tokens with new ones.
 *
 * Dependencies:
 * - AdminUserRepository: Manages AdminUser entities and provides database interaction for user-related operations.
 * - TokenRepository: Manages Token entities and handles token-related database interactions.
 * - PasswordEncoder: Encodes user passwords before saving them in the database.
 * - JwtService: Provides utilities for generating and validating JSON Web Tokens (JWT).
 * - AuthenticationManager: Handles the authentication of user credentials during login.
 *
 * Methods:
 * - register(): Registers a new admin user and generates JWT tokens for authentication.
 * - authenticate(): Authenticates a user with their credentials and issues authentication tokens.
 * - saveUserToken(): Saves or persists a token entity for a specific user in the database.
 * - revokeAllUserTokens(): Revokes all active tokens associated with a user, marking them as expired and invalid.
 * - refreshToken(): Refreshes the user's authentication tokens by validating the refresh token
 *   and issuing a new access token.
 *
 * This class is annotated with @Service, making it a Spring-managed service component
 * that can be injected into other components where authentication-related operations are required.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AdminUserRepository repository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public TokenResponse register(final RegisterRequest request) {
        final AdminUser adminUser = AdminUser.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        final AdminUser savedAdminUser = repository.save(adminUser);
        final String jwtToken = jwtService.generateToken(savedAdminUser);
        final String refreshToken = jwtService.generateRefreshToken(savedAdminUser);

        saveUserToken(savedAdminUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    @Override
    public TokenResponse authenticate(final AuthRequest request) {
        System.out.println("Attempting login with email: " + request.email() + ", password: " + request.password());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed:");
            e.printStackTrace();
            throw e;
        }
        final AdminUser adminUser = repository.findByEmail(request.email())
                .orElseThrow();
        final String accessToken = jwtService.generateToken(adminUser);
        final String refreshToken = jwtService.generateRefreshToken(adminUser);
        revokeAllUserTokens(adminUser);
        saveUserToken(adminUser, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public void saveUserToken(AdminUser adminUser, String jwtToken) {
        final Token token = Token.builder()
                .adminUser(adminUser)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    @Transactional // Asegúrate de que este método o el que lo llama tenga @Transactional
    public void revokeAllUserTokens(final AdminUser adminUser) {
        // Encuentra TODOS los tokens de ese usuario, activos o inactivos.
        // Esto es más seguro para evitar duplicados si un token "viejo" que no era "valid" causó el problema.
        final List<Token> allUserTokens = tokenRepository.findByAdminUser(adminUser); // Necesitarías este método en tu TokenRepository

        if (!allUserTokens.isEmpty()) {
            tokenRepository.deleteAll(allUserTokens); // <-- Elimina todos los tokens anteriores del usuario
        }
    }
    @Override
    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            return null;
        }

        final AdminUser adminUser = this.repository.findByEmail(userEmail).orElseThrow();
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, adminUser);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateRefreshToken(adminUser);
        revokeAllUserTokens(adminUser);
        saveUserToken(adminUser, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}
