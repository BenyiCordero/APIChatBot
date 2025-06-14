package com.chatBotStadistics.config;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.repository.TokenRepository;
import com.chatBotStadistics.repository.AdminUserRepository;
import com.chatBotStadistics.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * JwtAuthenticationFilter is a custom implementation of OncePerRequestFilter used to handle
 * JWT-based authentication for incoming HTTP requests. It inspects the Authorization header,
 * validates the JWT token, and sets the appropriate authentication context for the user.
 *
 * This filter ensures that requests with valid JWT tokens are authenticated,
 * while requests to authentication-specific endpoints (e.g., "/auth") bypass this filter.
 *
 * Key responsibilities:
 * - Extracts the JWT from the Authorization header.
 * - Validates the JWT token.
 * - Checks token expiration or revocation status via the TokenRepository.
 * - Fetches user details from the database using UserDetailsService and AdminUserRepository.
 * - Establishes an authenticated context for valid tokens.
 *
 * Dependencies:
 * - JwtService: Provides methods for extracting and validating JWT tokens.
 * - UserDetailsService: Loads user details for authentication.
 * - TokenRepository: Provides access to saved JWT tokens for validation.
 * - AdminUserRepository: Retrieves admin user details from the database.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    AdminUserRepository adminUserRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userEmail == null || authentication != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        final boolean isTokenExpiredOrRevoked = tokenRepository.findByToken(jwt)
                .map(token -> !token.getIsExpired() && !token.getIsRevoked())
                .orElse(false);


        if (isTokenExpiredOrRevoked) {
            final Optional<AdminUser> user = adminUserRepository.findByEmail(userEmail);

            if (user.isPresent()) {
                final boolean isTokenValid = jwtService.isTokenValid(jwt, user.get());

                if (isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
