package com.my.tradingapp.service;

import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.entity.user.User;

public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    // Used internally by security layer — returns full entity not DTO
    User findByEmail(String email);

    void deleteUser(Long id);
}

