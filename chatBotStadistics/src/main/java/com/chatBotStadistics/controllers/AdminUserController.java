package com.chatBotStadistics.controllers;

import com.chatBotStadistics.dto.AdminUserResponse;
import com.chatBotStadistics.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserRepository adminUserRepository;

    @GetMapping
    public List<AdminUserResponse> changePassword() {
        return adminUserRepository.findAll()
                .stream()
                .map(user -> new AdminUserResponse(user.getName(), user.getEmail()))
                .toList();
    }

}
