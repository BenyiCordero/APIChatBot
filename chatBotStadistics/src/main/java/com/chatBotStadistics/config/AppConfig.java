package com.chatBotStadistics.config;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AppConfig is a configuration class for setting up the beans required for authentication and security.
 * It provides Spring-managed beans for UserDetailsService, AuthenticationProvider, AuthenticationManager,
 * and PasswordEncoder to enable proper user authentication and password handling.
 *
 * The class relies on Spring Security features to authenticate users and manage their credentials.
 * It integrates with an AdminUserRepository to fetch user details from the database
 * and to configure authentication mechanisms.
 *
 * Declared beans:
 * - `userDetailsService`: Configures UserDetailsService to load user details from the given repository.
 * - `authenticationProvider`: Configures the AuthenticationProvider with a custom UserDetailsService
 *    and password encoding mechanism.
 * - `authenticationManager`: Provides the AuthenticationManager configured through the
 *    supplied AuthenticationConfiguration.
 * - `passwordEncoder`: Provides an instance of BCryptPasswordEncoder to handle password encoding.
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Autowired
    AdminUserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            final AdminUser adminUser = repository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(adminUser.getEmail())
                    .password(adminUser.getPassword())
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
