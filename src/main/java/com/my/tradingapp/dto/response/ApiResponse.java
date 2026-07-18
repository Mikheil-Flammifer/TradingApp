package com.my.tradingapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

// Generic wrapper used for every API response
// e.g. ApiResponse<UserResponse>, ApiResponse<PortfolioResponse>
@JsonInclude(JsonInclude.Include.NON_NULL)  // omit null fields from JSON
public record ApiResponse<T>(

        boolean success,
        String message,
        T data,                // the actual payload — null on error
        Instant timestamp

) {
    // Static factory helpers so services/controllers don't repeat boilerplate

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data, Instant.now());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }
}