package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.AdminUser;

import java.util.Optional;

public interface AdminUserService {

    Optional<AdminUser> findByEmail(String email);

}
