package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;
import com.chatBotStadistics.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The AdminUserService class is a service layer component responsible for
 * handling the business logic related to AdminUser entities.
 *
 * It implements the Spring Security UserDetailsService interface to manage
 * authentication by loading user-specific data.
 *
 * Core Responsibilities:
 * - Fetch AdminUser entities by their email address using `AdminUserRepository`.
 * - Provide user details necessary for authentication via Spring Security.
 *
 * Dependencies:
 * - AdminUserRepository: Used to interact with the database and fetch AdminUser entities.
 *
 * Primary Methods:
 * - findUserByEmail: Retrieves an AdminUser entity with the specified email
 *   address, if one exists.
 * - loadUserByUsername: Implements the Spring Security `UserDetailsService`
 *   interface method to load user-specific data needed for authentication.
 */
@Service
@RequiredArgsConstructor
public class AdminUserService implements UserDetailsService {

    @Autowired
    AdminUserRepository adminUserRepository;

    public Optional<AdminUser> findUserByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findUserByEmail(email)
                .map(user -> org.springframework.security.core.userdetails.User
                        .builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
