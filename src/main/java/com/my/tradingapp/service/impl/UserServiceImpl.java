package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.entity.user.User;
import com.my.tradingapp.mapper.UserMapper;
import com.my.tradingapp.repository.UserRepository;
import com.my.tradingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getUserById(Long id) {
        // TODO:
        // 1. Find user by id — throw NotFoundException if not found
        // 2. Map to UserResponse and return
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        // TODO:
        // 1. Find user by email — throw NotFoundException if not found
        // 2. Map to UserResponse and return
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User findByEmail(String email) {
        // TODO:
        // 1. Find and return full User entity (used by security layer)
        // 2. Throw NotFoundException if not found
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteUser(Long id) {
        // TODO:
        // 1. Find user by id — throw NotFoundException if not found
        // 2. Delete user (cascade will handle portfolio, watchlist etc.)
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
