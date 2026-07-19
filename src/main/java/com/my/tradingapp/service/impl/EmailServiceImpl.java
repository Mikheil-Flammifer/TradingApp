package com.my.tradingapp.service.impl;

import com.my.tradingapp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    @Override
    public void sendVerificationOtp(String toEmail, String otp) {
        String subject = "Verify your TradingApp account";
        String body = """
                <html>
                <body style="font-family: Arial, sans-serif; padding: 20px;">
                    <h2 style="color: #2c3e50;">Welcome to TradingApp!</h2>
                    <p>Thank you for registering. Please verify your account using the OTP below:</p>
                    <div style="background:#f4f4f4; padding:15px; border-radius:8px;
                                font-size:28px; font-weight:bold; letter-spacing:8px;
                                text-align:center; color:#2c3e50;">
                        %s
                    </div>
                    <p style="color:#888; margin-top:15px;">
                        This OTP expires in <strong>10 minutes</strong>.
                        If you did not register, please ignore this email.
                    </p>
                </body>
                </html>
                """.formatted(otp);
        sendEmail(toEmail, subject, body);
    }

    @Override
    public void sendPasswordResetOtp(String toEmail, String otp) {
        String subject = "Reset your TradingApp password";
        String body = """
                <html>
                <body style="font-family: Arial, sans-serif; padding: 20px;">
                    <h2 style="color: #2c3e50;">Password Reset Request</h2>
                    <p>We received a request to reset your password. Use the OTP below:</p>
                    <div style="background:#f4f4f4; padding:15px; border-radius:8px;
                                font-size:28px; font-weight:bold; letter-spacing:8px;
                                text-align:center; color:#e74c3c;">
                        %s
                    </div>
                    <p style="color:#888; margin-top:15px;">
                        This OTP expires in <strong>10 minutes</strong>.
                        If you did not request a password reset, please ignore this email.
                    </p>
                </body>
                </html>
                """.formatted(otp);
        sendEmail(toEmail, subject, body);
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            log.info("Email sent successfully to {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}