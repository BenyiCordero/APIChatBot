package com.chatBotStadistics.config;

import com.chatBotStadistics.domain.Token;
import com.chatBotStadistics.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * SecurityConfig defines the security configuration for the application using Spring Security.
 * It configures authentication, authorization, CORS, CSRF, and session management,
 * along with the integration of JWT-based authentication.
 *
 * This class relies on a combination of Spring Security's features and custom implementations
 * to enforce secure access to application endpoints. It integrates:
 * - AuthenticationProvider: Handles user authentication by delegating to a configured provider.
 * - JwtAuthenticationFilter: Intercepts requests to validate JWT tokens and set authentication contexts.
 * - TokenRepository: Manages token states such as expiration and revocation.
 *
 * Key features:
 * - Disables CSRF protection as it uses JWT for authentication.
 * - Configures CORS to allow requests from specified origins, methods, headers, and credentials.
 * - Sets up authorization rules:
 *   - Public access to endpoints matching "/auth/**".
 *   - Authentication required for all other endpoints.
 * - Configures stateless session management to handle JWT-based authentication.
 * - Adds a logout mechanism:
 *   - Expires and revokes the user's token upon logout.
 *   - Clears security context after successful logout.
 *
 * Components:
 * - `securityFilterChain`: Configures the HTTP security features, such as CORS, CSRF, authorization rules,
 *   and JWT filter integration.
 * - `logout`: Handles token expiration and revocation tasks during logout, ensuring tokens can no longer be used.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    TokenRepository tokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    //Cambiar para el front
                    configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "https://ca40-201-163-190-4.ngrok-free.app", "http://localhost:8080", "https://chatbotstadistics.vercel.app"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
                    configuration.setAllowedHeaders(List.of("*","ngrok-skip-browser-warning"));
                    configuration.setExposedHeaders(List.of("Authorization"));
                    configuration.setAllowCredentials(true);
                    return configuration;
                }))
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/auth/**")
                                .permitAll()
                                .requestMatchers("/consultas/**")
                                .authenticated()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(this::logout)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
    private void logout(
            final HttpServletRequest request, final HttpServletResponse response,
            final Authentication authentication
    ) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwt = authHeader.substring(7);
        final Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setIsExpired(true);
            storedToken.setIsRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }

}
