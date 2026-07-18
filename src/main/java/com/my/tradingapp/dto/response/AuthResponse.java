package com.my.tradingapp.dto.response;

public record AuthResponse(
        String token,
        String tokenType,   // always "Bearer"
        UserResponse user
) {}
