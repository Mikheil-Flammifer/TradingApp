package com.my.tradingapp.service.impl;

import com.my.tradingapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationOtp(String toEmail, String otp) {
        // TODO:
        // 1. Build subject: "Verify your TradingApp account"
        // 2. Build HTML body with OTP code and expiry info
        // 3. Call sendEmail()
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void sendPasswordResetOtp(String toEmail, String otp) {
        // TODO:
        // 1. Build subject: "Reset your TradingApp password"
        // 2. Build HTML body with OTP code and expiry info
        // 3. Call sendEmail()
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        // TODO:
        // 1. Create MimeMessage via mailSender.createMimeMessage()
        // 2. Set from, to, subject, body (HTML)
        // 3. Call mailSender.send()
        // 4. Wrap in try/catch — throw MailSendException on failure
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
