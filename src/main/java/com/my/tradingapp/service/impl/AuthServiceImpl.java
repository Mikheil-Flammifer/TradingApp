package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.request.ForgotPasswordRequest;
import com.my.tradingapp.dto.request.LoginRequest;
import com.my.tradingapp.dto.request.RegisterRequest;
import com.my.tradingapp.dto.request.ResetPasswordRequest;
import com.my.tradingapp.dto.request.VerifyOtpRequest;
import com.my.tradingapp.dto.response.AuthResponse;
import com.my.tradingapp.entity.user.UserRole;
import com.my.tradingapp.entity.user.User;
import com.my.tradingapp.exception.AppException;
import com.my.tradingapp.mapper.UserMapper;
import com.my.tradingapp.repository.UserRepository;
import com.my.tradingapp.security.JwtService;
import com.my.tradingapp.service.AuthService;
import com.my.tradingapp.service.EmailService;
import com.my.tradingapp.service.PortfolioService;
import com.my.tradingapp.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PortfolioService portfolioService;
    private final WatchlistService watchlistService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    private static final long OTP_EXPIRY_MS = 10 * 60 * 1000; // 10 minutes
    private static final Random random = new SecureRandom();

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check email and username not already taken
        if (userRepository.existsByEmail(request.email()))
            throw AppException.conflict("Email already registered: " + request.email());
        if (userRepository.existsByUserName(request.username()))
            throw AppException.conflict("Username already taken: " + request.username());

        // Generate OTP
        String otp = generateOtp();

        // Build and save user
        User user = User.builder()
                .userName(request.username())
                .email(request.email())
                .userPassword(passwordEncoder.encode(request.password()))
                .userRole(UserRole.USER)
                .isAccountVerified(false)
                .otp(otp)
                .otpExpiresAt(System.currentTimeMillis() + OTP_EXPIRY_MS)
                .build();
        userRepository.save(user);

        // Create portfolio and watchlist
        portfolioService.createPortfolioForUser(user);
        watchlistService.createWatchlistForUser(user);

        // Send verification OTP email
        emailService.sendVerificationOtp(user.getEmail(), otp);

        // Generate JWT and return
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getUserPassword(), java.util.List.of());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, "Bearer", userMapper.toResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Find user
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> AppException.unauthorized("Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(request.password(), user.getUserPassword()))
            throw AppException.unauthorized("Invalid email or password");

        // Check verified
        if (!user.getIsAccountVerified())
            throw AppException.forbidden("Account not verified. Please check your email for the OTP.");

        // Generate JWT
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getUserPassword(), java.util.List.of());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, "Bearer", userMapper.toResponse(user));
    }

    @Override
    @Transactional
    public void verifyAccount(VerifyOtpRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> AppException.notFound("User not found"));

        if (user.getIsAccountVerified())
            throw AppException.badRequest("Account already verified");

        if (!request.otp().equals(user.getOtp()))
            throw AppException.badRequest("Invalid OTP");

        if (System.currentTimeMillis() > user.getOtpExpiresAt())
            throw AppException.badRequest("OTP has expired. Please request a new one.");

        user.setIsAccountVerified(true);
        user.setOtp(null);
        user.setOtpExpiresAt(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> AppException.notFound("User not found"));

        if (user.getIsAccountVerified())
            throw AppException.badRequest("Account already verified");

        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiresAt(System.currentTimeMillis() + OTP_EXPIRY_MS);
        userRepository.save(user);
        emailService.sendVerificationOtp(email, otp);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> AppException.notFound("User not found"));

        String otp = generateOtp();
        user.setResetOtp(otp);
        user.setResetOtpExpiresAt(System.currentTimeMillis() + OTP_EXPIRY_MS);
        userRepository.save(user);
        emailService.sendPasswordResetOtp(request.email(), otp);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> AppException.notFound("User not found"));

        if (!request.otp().equals(user.getResetOtp()))
            throw AppException.badRequest("Invalid OTP");

        if (System.currentTimeMillis() > user.getResetOtpExpiresAt())
            throw AppException.badRequest("OTP has expired. Please request a new one.");

        user.setUserPassword(passwordEncoder.encode(request.newPassword()));
        user.setResetOtp(null);
        user.setResetOtpExpiresAt(null);
        userRepository.save(user);
    }

    // Generate a 6-digit OTP
    private String generateOtp() {
        return String.format("%06d", random.nextInt(999999));
    }
}