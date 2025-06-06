package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.dto.AuthRequest;
import com.chatBotStadistics.dto.RegisterRequest;
import com.chatBotStadistics.dto.TokenResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing user authentication and token-related operations.
 *
 * This interface defines methods for user registration, authentication,
 * and handling of JSON Web Tokens (JWT) such as saving, revoking, and refreshing tokens.
 * It serves as the contract for implementing authentication-related functionality
 * in the application.
 */
@Service
public interface AuthService {

    TokenResponse register(final RegisterRequest request);
    TokenResponse authenticate(final AuthRequest request);
    void saveUserToken(AdminUser adminUser, String jwtToken);
    void revokeAllUserTokens(final AdminUser adminUser);
    TokenResponse refreshToken(@NotNull final String authentication);


}
