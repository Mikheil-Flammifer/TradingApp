package com.my.tradingapp.service;

public interface EmailService {

    // Send OTP for account verification on register
    void sendVerificationOtp(String toEmail, String otp);

    // Send OTP for password reset
    void sendPasswordResetOtp(String toEmail, String otp);

    // Generic email sender used by the above methods
    void sendEmail(String toEmail, String subject, String body);
}