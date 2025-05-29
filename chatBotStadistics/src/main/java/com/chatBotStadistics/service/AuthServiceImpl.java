package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.domain.Token;
import com.chatBotStadistics.dto.AuthRequest;
import com.chatBotStadistics.dto.RegisterRequest;
import com.chatBotStadistics.dto.TokenResponse;
import com.chatBotStadistics.repository.TokenRepository;
import com.chatBotStadistics.repository.AdminUserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;


import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AdminUserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(AdminUserRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

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
    public void revokeAllUserTokens(final AdminUser adminUser) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(adminUser.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
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
