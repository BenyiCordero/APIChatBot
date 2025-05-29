package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.dto.AuthRequest;
import com.chatBotStadistics.dto.RegisterRequest;
import com.chatBotStadistics.dto.TokenResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    TokenResponse register(final RegisterRequest request);
    TokenResponse authenticate(final AuthRequest request);
    void saveUserToken(AdminUser adminUser, String jwtToken);
    void revokeAllUserTokens(final AdminUser adminUser);
    TokenResponse refreshToken(@NotNull final String authentication);


}
