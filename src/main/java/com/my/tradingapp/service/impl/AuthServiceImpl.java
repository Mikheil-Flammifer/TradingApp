package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.request.ForgotPasswordRequest;
import com.my.tradingapp.dto.request.LoginRequest;
import com.my.tradingapp.dto.request.RegisterRequest;
import com.my.tradingapp.dto.request.ResetPasswordRequest;
import com.my.tradingapp.dto.request.VerifyOtpRequest;
import com.my.tradingapp.dto.response.AuthResponse;
import com.my.tradingapp.mapper.UserMapper;
import com.my.tradingapp.repository.UserRepository;
import com.my.tradingapp.service.AuthService;
import com.my.tradingapp.service.EmailService;
import com.my.tradingapp.service.PortfolioService;
import com.my.tradingapp.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PortfolioService portfolioService;
    private final WatchlistService watchlistService;
    private final UserMapper userMapper;

    // JwtService will be injected here once created

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // TODO:
        // 1. Check if email/username already exists
        // 2. Hash password
        // 3. Generate OTP + expiry
        // 4. Save user
        // 5. Create portfolio and watchlist for user
        // 6. Send verification OTP email
        // 7. Return AuthResponse with JWT
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // TODO:
        // 1. Find user by email
        // 2. Check password matches
        // 3. Check account is verified
        // 4. Generate JWT
        // 5. Return AuthResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void verifyAccount(VerifyOtpRequest request) {
        // TODO:
        // 1. Find user by email
        // 2. Check OTP matches
        // 3. Check OTP not expired
        // 4. Mark account as verified
        // 5. Clear OTP fields
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void resendOtp(String email) {
        // TODO:
        // 1. Find user by email
        // 2. Check account not already verified
        // 3. Generate new OTP + expiry
        // 4. Save user
        // 5. Send OTP email
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        // TODO:
        // 1. Find user by email
        // 2. Generate reset OTP + expiry
        // 3. Save user
        // 4. Send password reset OTP email
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        // TODO:
        // 1. Find user by email
        // 2. Check reset OTP matches
        // 3. Check OTP not expired
        // 4. Hash new password and save
        // 5. Clear reset OTP fields
        throw new UnsupportedOperationException("Not implemented yet");
    }
}