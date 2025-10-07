package com.chatBotStadistics.controllers;

import com.chatBotStadistics.dto.AdminUserResponse;
import com.chatBotStadistics.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller that handles the administration of users in the application.
 * This class provides endpoints to interact with user data.
 *
 * The controller is annotated with {@code @RestController} and maps requests
 * to the base URI "/usuarios" using {@code @RequestMapping}.
 *
 * It uses the {@code AdminUserRepository} to fetch user data and transform it
 * into a response format defined by {@code AdminUserResponse}.
 *
 * Dependencies are injected through the constructor using the {@code @RequiredArgsConstructor} annotation.
 */
@RestController
@RequestMapping("/usuarios")
public class AdminUserController {

    private final AdminUserRepository adminUserRepository;

    public AdminUserController(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @GetMapping
    public List<AdminUserResponse> changePassword() {
        return adminUserRepository.findAll()
                .stream()
                .map(user -> new AdminUserResponse(user.getName(), user.getEmail()))
                .toList();
    }

}
