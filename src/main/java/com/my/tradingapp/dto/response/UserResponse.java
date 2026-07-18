package com.my.tradingapp.dto.response;

import com.my.tradingapp.entity.user.UserRole;

import java.time.Instant;

public record UserResponse(

        Long id,
        String username,
        String email,
        UserRole userRole,
        Boolean isAccountVerified,
        Instant createdAt

        // no password, no OTP fields

) {}
