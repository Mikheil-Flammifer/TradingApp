package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.entity.user.User;
import com.my.tradingapp.exception.AppException;
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> AppException.notFound("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> AppException.notFound("User not found with email: " + email));
        return userMapper.toResponse(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> AppException.notFound("User not found with email: " + email));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> AppException.notFound("User not found with id: " + id));
        userRepository.delete(user);
    }
}