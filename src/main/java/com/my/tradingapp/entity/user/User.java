package com.my.tradingapp.entity.user;

import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @JsonIgnore
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private UserRole userRole = UserRole.USER;

    @Column(name = "verified")
    @Builder.Default
    private Boolean isAccountVerified = false;

    @Column(name = "reset_otp")
    private String resetOtp;

    @Column(name = "reset_otp_expires_at")
    private Long resetOtpExpiresAt;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expires_at")
    private Long otpExpiresAt;

    @Column(name = "user_create_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}