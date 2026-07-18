package com.my.tradingapp.service;

import com.my.tradingapp.dto.request.ForgotPasswordRequest;
import com.my.tradingapp.dto.request.LoginRequest;
import com.my.tradingapp.dto.request.RegisterRequest;
import com.my.tradingapp.dto.request.ResetPasswordRequest;
import com.my.tradingapp.dto.request.VerifyOtpRequest;
import com.my.tradingapp.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void verifyAccount(VerifyOtpRequest request);

    void resendOtp(String email);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
