package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

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
