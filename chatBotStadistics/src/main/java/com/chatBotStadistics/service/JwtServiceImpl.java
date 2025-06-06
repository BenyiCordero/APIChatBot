package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * Service implementation for managing JSON Web Tokens (JWT) related operations.
 *
 * This class provides functionality for generating, validating, and extracting information
 * from JWTs. It also handles the creation of tokens with custom expiration times,
 * as well as methods to check token validity and expiration.
 *
 * The implementation relies on a secret key and expiration times configured as properties
 * in the application to ensure security and token lifecycle management.
 *
 * Methods:
 * - extractUsername: Extracts the username (subject) from a JWT.
 * - generateToken: Generates an access token for a given AdminUser.
 * - generateRefreshToken: Generates a refresh token for a given AdminUser.
 * - buildToken: Constructs a JWT for a given AdminUser with a specified expiration time.
 * - isTokenValid: Validates a token by verifying the username and expiration status.
 * - isTokenExpired: Checks if a token has expired based on the expiration claim.
 * - extractExpiration: Retrieves the expiration date from a JWT.
 * - getSignInKey: Provides the signing key derived from the configured secret key.
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public String generateToken(AdminUser adminUser) {
        return buildToken(adminUser, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(AdminUser adminUser) {
        return buildToken(adminUser, refreshExpiration);
    }

    @Override
    public String buildToken(AdminUser adminUser, long expiration) {
        return Jwts
                .builder()
                .claims(Map.of("name", adminUser.getName()))
                .subject(adminUser.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, AdminUser adminUser) {
        final String username = extractUsername(token);
        return (username.equals(adminUser.getEmail())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());    }

    @Override
    public Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    @Override
    public SecretKey getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
