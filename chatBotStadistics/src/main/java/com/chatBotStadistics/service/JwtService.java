package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Service interface for managing JSON Web Token (JWT) related operations.
 *
 * This interface provides methods to handle JWT functionality, including:
 * - Extracting the username from a token.
 * - Generating tokens and refresh tokens for a given user.
 * - Building tokens with a custom expiration.
 * - Validating tokens against user details.
 * - Checking if a token is expired.
 * - Extracting the expiration date from a token.
 * - Retrieving the secret key used for signing the tokens.
 */
@Service
public interface JwtService {

    String extractUsername(String token);

    String generateToken(final AdminUser adminUser); // Cambiado a UserDetails

    String generateRefreshToken(final AdminUser adminUser); // Cambiado a UserDetails

    String buildToken(final AdminUser adminUser, final long expiration); // Cambiado a UserDetails

    boolean isTokenValid(String token, AdminUser adminUser); // Cambiado a UserDetails

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    SecretKey getSignInKey();

}
